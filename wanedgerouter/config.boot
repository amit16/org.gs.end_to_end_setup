interfaces {
    ethernet eth0 {
        address 50.50.11.2/24
        duplex auto
        hw-id 00:50:56:a7:12:cf
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address 50.50.13.1/24
        duplex auto
        hw-id 00:50:56:a7:18:2d
        smp_affinity auto
        speed auto
    }
    ethernet eth2 {
        address 50.50.12.1/24
        duplex auto
        hw-id 00:50:56:a7:5a:78
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
protocols {
    bgp 4502 {
        neighbor 50.50.11.1 {
            remote-as 4501
        }
        neighbor 50.50.12.2 {
            remote-as 4503
        }
        neighbor 50.50.13.2 {
            remote-as 4504
        }
        parameters {
            router-id 4.5.0.2
        }
        redistribute {
            connected {
            }
            static {
            }
        }
    }
    static {
    }
}
service {
    ssh {
        port 22
    }
}
system {
    config-management {
        commit-revisions 20
    }
    console {
        device ttyS0 {
            speed 9600
        }
    }
    host-name vyos
    login {
        user vyos {
            authentication {
                encrypted-password $1$xI912Eur$qewjZC46tmVBO.nrl81sN/
            }
            level admin
        }
    }
    ntp {
        server 0.pool.ntp.org {
        }
        server 1.pool.ntp.org {
        }
        server 2.pool.ntp.org {
        }
    }
    package {
        auto-sync 1
        repository community {
            components main
            distribution hydrogen
            password ""
            url http://packages.vyos.net/vyos
            username ""
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone UTC
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "cluster@1:config-management@1:conntrack-sync@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@4:nat@4:qos@1:quagga@2:system@6:vrrp@1:wanloadbalance@3:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: VyOS 1.0.5 */
