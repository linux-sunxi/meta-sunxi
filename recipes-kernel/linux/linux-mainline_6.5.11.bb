require linux-mainline.inc

DESCRIPTION = "Mainline Longterm Linux kernel"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

# Board Specific features
KERNEL_FEATURES:prepend:orange-pi-3lts = " bsp/orange-pi-3lts/orange-pi-3lts-6_5.scc bsp/uwe5622/uwe5622-6_5.scc"


SRC_URI[sha256sum] = "2ee24af9282b80923b2da56b70aad7df2e8ee4e3f076452e05ba66be2059b519"

