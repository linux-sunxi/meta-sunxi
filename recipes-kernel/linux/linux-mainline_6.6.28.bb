require linux-mainline.inc

DESCRIPTION = "Mainline Longterm Linux kernel"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI[sha256sum] = "818716ed13e7dba6aaeae24e3073993e260812ed128d10272e94b922ee6d3394"


KERNEL_FEATURES:prepend:orange-pi-3lts = " bsp/orange-pi-3lts/orange-pi-3lts-6_5.scc bsp/uwe5622/uwe5622-6_6.scc bsp/orange-pi-3lts/fix-rtc.scc"
