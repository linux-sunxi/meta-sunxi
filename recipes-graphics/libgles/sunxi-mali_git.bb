DESCRIPTION = "libGLES for the A10/A13 Allwinner processor with Mali 400 (X11)"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://README;md5=1b81a178e80ee888ee4571772699ab2c"

COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i)"

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.
EXCLUDE_FROM_WORLD = "1"
PROVIDES = "virtual/libgles1 virtual/libgles2 virtual/egl"

# There's only hardfp version available
python __anonymous() {
    tunes = d.getVar("TUNE_FEATURES", True)
    if not tunes:
        return
    if "callconvention-hard" not in tunes:
        pkgn = d.getVar("PN", True)
        pkgv = d.getVar("PV", True)
        raise bb.parse.SkipPackage("%s-%s ONLY supports hardfp mode for now" % (pkgn, pkgv))
}

SRCREV = "d343311efc8db166d8371b28494f0f27b6a58724"
SRC_URI = "git://github.com/linux-sunxi/sunxi-mali.git;protocol=https;branch=master \
           file://0001-Add-EGLSyncKHR-EGLTimeKHR-and-GLChar-definition.patch \
           file://0002-Add-missing-GLchar-definition.patch \
           file://0003-Fix-sed-to-replace-by-the-correct-var.patch \
           file://0001-fix-test-build.patch \
           "

DEPENDS = "libdrm xorgproto libump patchelf-native"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"
PACKAGECONFIG[wayland] = "EGL_TYPE=framebuffer,,,"
PACKAGECONFIG[x11] = "EGL_TYPE=x11,,virtual/libx11 libxau libxdmcp libdri2,"

# Inhibit warnings about files being stripped, we can't do anything about it.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

do_configure() {
         DESTDIR=${D}/ VERSION=r3p0 ABI=armhf make ${EXTRA_OEMAKE} config
}

do_install() {
    make -f Makefile.pc

    # install headers
    install -d -m 0755 ${D}${includedir}/EGL
    install -m 0755 ${S}/include/EGL/*.h ${D}${includedir}/EGL/
    install -d -m 0755 ${D}${includedir}/GLES
    install -m 0755 ${S}/include/GLES/*.h ${D}${includedir}/GLES/
    install -d -m 0755 ${D}${includedir}/GLES2
    install -m 0755 ${S}/include/GLES2/*.h ${D}${includedir}/GLES2/
    install -d -m 0755 ${D}${includedir}/KHR
    install -m 0755 ${S}/include/KHR/*.h ${D}${includedir}/KHR/

    # Copy the .pc files
    install -d -m 0755 ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/egl.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${S}/gles_cm.pc ${D}${libdir}/pkgconfig/
    install -m 0644 ${S}/glesv2.pc ${D}${libdir}/pkgconfig/

    install -d ${D}${libdir}
    install -d ${D}${includedir}

    make libdir=${D}${libdir}/ includedir=${D}${includedir}/ install
    make libdir=${D}${libdir}/ includedir=${D}${includedir}/ install -C include
    rm -f ${D}${includedir}/KHR/khrplatform.h

    # Fix .so name and create symlinks, binary package provides .so wich can't be included directly in package without triggering the 'dev-so' QA check
    # Packages like xf86-video-fbturbo dlopen() libUMP.so, so we do need to ship the .so files in ${PN}

    mv ${D}${libdir}/libMali.so ${D}${libdir}/libMali.so.3
    patchelf --set-soname libMali.so.3 ${D}${libdir}/libMali.so.3
    ln -sf libMali.so.3 ${D}${libdir}/libMali.so

    for flib in libEGL.so.1.4 libGLESv1_CM.so.1.1 libGLESv2.so.2.0 ; do
        rm ${D}${libdir}/$flib
        ln -sf libMali.so.3 ${D}${libdir}/$flib
    done

    DESTDIR=${D}/ VERSION=r3p0 ABI=armhf make ${EXTRA_OEMAKE} test
    install -d ${D}${bindir}
    install -m 0755 ${S}/test/test ${D}${bindir}/sunximali-test
}

# Packages like xf86-video-fbturbo dlopen() libUMP.so, so we do need to ship the .so files in ${PN}
PACKAGES =+ "${PN}-test"

RPROVIDES:${PN} += "libGLESv2.so libEGL.so libGLESv2.so libGLESv1_CM.so libMali.so"
RDEPENDS:${PN}-test = "${PN}"

FILES:${PN} += "${libdir}/lib*.so"
FILES:${PN}-dev = "${includedir} ${libdir}/pkgconfig/*"
FILES:${PN}-test = "${bindir}/sunximali-test"

# These are closed binaries generated elsewhere so don't check ldflags & text relocations
INSANE_SKIP:${PN} = "dev-so ldflags textrel"
INSANE_SKIP:${PN}-test = "dev-so ldflags textrel"
