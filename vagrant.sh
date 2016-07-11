#!/bin/bash
echo -e "\nInstalling Virtual Box\n"
sudo apt-get install virtualbox

echo -e "\nInstalling Vagrant\n"
sudo apt-get install vagrant

echo -e "\nImage Box (Ubuntu 12.04 64-bit)\n"
sudo vagrant box add hashicorp/precise64
