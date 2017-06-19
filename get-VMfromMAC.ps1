#input - mac_address
#searches for the mac address in vCenter
#on running asks for vcenter credentials
#An optional way is to write : Connect-VIServer upfront.

$mac = Read-Host "What mac address do you want to lookup (format:11:22:33:44:55:66 )"
get-vm | Get-NetworkAdapter | where {$_.macaddress -eq "$mac"} | select parent,macaddress
