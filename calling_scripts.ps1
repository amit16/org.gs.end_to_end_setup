# Import the common library functions 
Import-Module ".\modules.ps1"
[xml]$xml = Get-Content -Path ".\setup_config.xml"


$host_info = $xml.config.esxi
$vSwitch_info = $xml.Config.vSwitch
$portgroup_info = $xml.Config.portgroup
$vm_info = $xml.Config.VM

##Connect to Host	
connect_host($host_info)


##Creating the vSwitch
#BRANCH_LAN_vSwitch
createvswitch $vSwitch_info.name1 $host_info.ipaddress
#WAN_vSwitch
createvswitch $vSwitch_info.name2 $host_info.ipaddress



##Creating the portgroups
#branch_lan_portgroup
createvpg $portgroup_info.br_lan.name $portgroup_info.br_lan.vlan_id $vSwitch_info.name1

#branch_lan_trunk
createvpg $portgroup_info.lan_trunk.name $portgroup_info.lan_trunk.vlan_id $vSwitch_info.name1
#setPromiscuous $portgroup_info.lan_trunk.name

#private_wan_trunk
createvpg $portgroup_info.wan_trunk.name $portgroup_info.wan_trunk.vlan_id $vSwitch_info.name2
#setPromiscuous $portgroup_info.wan_trunk.name

#branch_wan_network
createvpg $portgroup_info.br_wan.name $portgroup_info.br_wan.vlan_id $vSwitch_info.name2
#setPromiscuous $portgroup_info.wan_trunk.name

#branch_to_wanedge_portgroup
createvpg $portgroup_info.br_WE.name $portgroup_info.br_WE.vlan_id $vSwitch_info.name2

#wan_edge_to_coreRouter_portgroup
createvpg $portgroup_info.WE_core.name $portgroup_info.WE_core.vlan_id $vSwitch_info.name2

#wanedge_to_HUB_portgroup
createvpg $portgroup_info.hub_WE.name $portgroup_info.hub_WE.vlan_id $vSwitch_info.name2

#coreRouter_to_HUB_portgroup
createvpg $portgroup_info.hub_Core.name $portgroup_info.hub_Core.vlan_id $vSwitch_info.name2

#coreRouter_to_Server_portgroup
createvpg $portgroup_info.core_server.name $portgroup_info.core_server.vlan_id $vSwitch_info.name2



##Creating the Virtual Machines
#create lan-side client_machine
createclientvm $vm_info.client_lan.name $host_info.ipaddress $vm_info.client_lan.datastore $portgroup_info.br_lan.name $vm_info.client_lan.isopath

#create branch router
$Branch_Network_Name = $portgroup_info.br_wan.name, $portgroup_info.br_WE.name #VM has multiple arg for portgroup
createroutervm $vm_info.br_router.name $host_info.ipaddress $vm_info.br_router.datastore $Branch_Network_Name $vm_info.br_router.isopath

#create wanedge router
$WanEdge_Network_Name = $portgroup_info.br_WE.name, $portgroup_info.WE_core.name, $portgroup_info.hub_WE.name
createroutervm $vm_info.WE_router.name $host_info.ipaddress $vm_info.WE_router.datastore $WanEdge_Network_Name $vm_info.WE_router.isopath

#create core router
$Core_Network_Name = $portgroup_info.WE_core.name, $portgroup_info.hub_Core.name, $portgroup_info.internet.name, $portgroup_info.core_server.name
createroutervm $vm_info.Core_router.name $host_info.ipaddress $vm_info.Core_router.datastore $Core_Network_Name $vm_info.Core_router.isopath

#create server-side machine
createclientvm $vm_info.server.name $host_info.ipaddress $vm_info.server.datastore $portgroup_info.core_server.name $vm_info.server.isopath

#Configure Routers
#Branch Router
configrouter $host_info $vm_info.br_router.name $vm_info.br_router.configfilepath

#WanEdge Router
configrouter $host_info $vm_info.WE_router.name $vm_info.WE_router.configfilepath

#Core Router
configrouter $host_info $vm_info.Core_router.name $vm_info.Core_router.configfilepath


##Disconnect Host
disconnect_host($xml.config.esxi)


