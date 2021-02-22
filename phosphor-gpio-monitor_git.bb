SUMMARY = "Phosphor GPIO monitor application"
DESCRIPTION = "Application to monitor gpio assertions"
HOMEPAGE = "http://github.com/openbmc/phosphor-gpio-monitor"
PR = "r1"
PV = "1.0+git${SRCPV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
inherit meson pkgconfig
inherit obmc-phosphor-dbus-service

GPIO_PACKAGES = " \
        ${PN}-monitor \
        ${PN}-presence \
"

PACKAGE_BEFORE_PN += "${GPIO_PACKAGES}"
ALLOW_EMPTY_${PN} = "1"
SYSTEMD_PACKAGES = "${GPIO_PACKAGES}"

RPROVIDES_${PN}-monitor += "virtual/obmc-gpio-monitor"
RPROVIDES_${PN}-presence += "virtual/obmc-gpio-presence"

PROVIDES += "virtual/obmc-gpio-monitor"
PROVIDES += "virtual/obmc-gpio-presence"

DEPENDS += "sdbusplus"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "libevdev"
DEPENDS += "phosphor-logging"
DEPENDS += "systemd"
DEPENDS += "boost"
DEPENDS += "libgpiod"
DEPENDS += "cli11"
DEPENDS += "nlohmann-json"

SYSTEMD_SERVICE_${PN}-monitor += "phosphor-multi-gpio-monitor.service"
SYSTEMD_SERVICE_${PN}-monitor += "phosphor-gpio-monitor@.service"
SYSTEMD_SERVICE_${PN}-presence += "phosphor-gpio-presence@.service"

FILES_${PN}-monitor += "${bindir}/phosphor-gpio-monitor"
FILES_${PN}-monitor += "${bindir}/phosphor-multi-gpio-monitor"
FILES_${PN}-monitor += "${bindir}/phosphor-gpio-util"
FILES_${PN}-monitor += "${base_libdir}/udev/rules.d/99-gpio-keys.rules"
FILES_${PN}-presence += "${bindir}/phosphor-gpio-presence"


SRC_URI_append = "file://phosphor-multi-gpio-monitor.json"
SRC_URI_append = "file://hand_switch_position.service"
SRC_URI_append = "file://phosphor-multi-gpio-monitor.service"
SRC_URI_append = "file://hand_switch_position.sh"
SRC_URI_append = "file://hand_switch_position_sol.sh"
SRC_URI_append = "file://hand_switch_position_sol.service"


SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "hand_switch_position.service phosphor-multi-gpio-monitor.service hand_switch_position_sol.service "



do_install_append() {

     install -m 0755 -d ${D}/usr/share/phosphor-gpio-monitor
    

     install -m 0644 -D ${WORKDIR}/phosphor-multi-gpio-monitor.json \
                   ${D}/usr/share/phosphor-gpio-monitor


     install -m 0644 -D ${WORKDIR}/hand_switch_position.service \
                   ${D}/lib/systemd/system

     install -m 0755 -D ${WORKDIR}/hand_switch_position.sh \
                   ${D}/usr/bin

     install -m 0755 -D ${WORKDIR}/hand_switch_position_sol.sh \
                   ${D}/usr/bin

     install -m 0644 -D ${WORKDIR}/hand_switch_position_sol.service \
                   ${D}/lib/systemd/system

     install -m 0644 -D ${WORKDIR}/phosphor-multi-gpio-monitor.service \
                   ${D}/lib/systemd/system
}

#SRC_URI += "git://github.com/openbmc/phosphor-gpio-monitor"
SRC_URI += "git://github.com/HCLOpenBMC/phosphor-gpio-monitor.git;protocol=http;branch=test"
SRCREV = "6f8645ca2fecc5c4af7817fb9e01f87d17e7f65a" 
S = "${WORKDIR}/git"
