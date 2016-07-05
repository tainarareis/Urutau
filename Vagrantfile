# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrant in version 2
Vagrant.configure(2) do |config|
	#Ubuntu 64 bits
    config.vm.box = "hashicorp/precise64"

	#Config define a machine called web
	config.vm.define :my_virtualbox do |web_config|
        # Allow accessing "localhost:8080" to access port 80 on the guest machine.
        config.vm.network "forwarded_port", guest: 80, host: 8090

        # Create a private network, which allows host-only access to the machine
        # using a specific IP.
        web_config.vm.network "private_network", ip: "192.168.50.10"
    end

    # Check box update, its equivalent to the command
    # `vagrant box outdated` but its recomend to check automaticaticaly
    config.vm.box_check_update = false

    # Share an additional folder to the guest VM
    # First argument: the path on the host to the actual folder
    # Second argument: the path on the guest to mount the folder
    #config.vm.synced_folder "../data", "/vagrant_data"

    #Config puppet as provisioner
    config.vm.provision :puppet do |puppet|
        puppet.manifests_path = "puppet/manifests"
        puppet.manifest_file = "default.pp" 
        puppet.module_path = "puppet/modules"
    end    
end
