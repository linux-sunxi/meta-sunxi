
DESCRIPTION = "Support for sunxi DT overlays"
LICENSE = "MIT"


SUNXI_OVERLAYS_ENABLE ??= ""

require sunxi-overlays-builtin.inc

inherit devicetree

