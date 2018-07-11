interfaces {
    ethernet eth0 {
        address 10.10.110.2/24
        duplex auto
        hw-id 00:50:56:a7:7a:f5
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address 10.10.120.1/24
        duplex auto
        hw-id 00:50:56:a7:4a:c2
        smp_affinity auto
        speed auto
    }
    ethernet eth2 {
        address 10.10.140.1/24
        duplex auto
        hw-id 00:50:56:a7:6e:19
        smp_affinity auto
        speed auto
    }
    ethernet eth3 {
        address dhcp
        duplex auto
        hw-id 00:50:56:a7:7d:c2
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
nat {
    source {
        rule 1 {
            description internet
            outbound-interface eth3
            translation {
                address masquerade
            }
        }
    }
}
protocols {
    bgp 6602 {
        neighbor 10.10.110.1 {
            remote-as 6601
        }
        neighbor 10.10.120.2 {
            remote-as 6603
        }
        parameters {
            router-id 6.6.6.6
        }
        redistribute {
            connected {
            }
        }
    }
}
service {
    dhcp-server {
        disabled true
        shared-network-name dc_lan_network {
            authoritative disable
            subnet 10.10.140.0/24 {
                default-router 10.10.140.1
                lease 86400
                start 10.10.140.10 {
                    stop 10.10.140.50
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
                encrypted-password $1$mwfKrO/z$GzvCCJki08duP4VZ5rOJU1
                plaintext-password ""
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
            distribution helium
            password ""
            url http://packages.vyos.net/vyos
            username ""
        }
        repository squeeze-backports {
            components main
            distribution squeeze-backports
            password ""
            url http://backports.debian.org/debian-backports
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
/* Release version: VyOS 1.1.6 */
