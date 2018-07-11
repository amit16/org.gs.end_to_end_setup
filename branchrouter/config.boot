interfaces {
    ethernet eth0 {
        address 50.50.20.1/24
        duplex auto
        hw-id 00:50:56:a7:17:41
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address 50.50.21.1/24
        duplex auto
        hw-id 00:50:56:a7:19:d4
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
protocols {
    bgp 4505 {
        neighbor 50.50.21.2 {
            remote-as 4502
        }
        parameters {
            router-id 4.5.0.5
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
    dhcp-server {
        disabled false
        shared-network-name lan-network2 {
            authoritative disable
            subnet 50.50.20.0/24 {
                default-router 50.50.20.1
                dns-server 8.8.8.8
                dns-server 4.2.2.2
                lease 295200
                start 50.50.20.10 {
                    stop 50.50.20.250
                }
            }
        }
    }
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
                encrypted-password $1$maBHs9sO$dVC9oFVz/AdX8K1lFSRjd1
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
