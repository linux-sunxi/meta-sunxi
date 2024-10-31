# Enable lima and panfrost Mesa drivers
PACKAGECONFIG:append:use-mailine-graphics = " lima panfrost"
# Enable KMS renderonly Mesa support
# See https://cgit.freedesktop.org/mesa/mesa/commit/?id=2b780fe89300199f2334539aa1678e9b68f0434f
GALLIUMDRIVERS:append:use-mailine-graphics = ",kmsro"
