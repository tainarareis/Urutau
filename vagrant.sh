#!/bin/bash
echo -e "\nInstalling Virtual Box\n"
sudo apt-get install virtualbox

echo -e "\nInstalling Vagrant\n"
sudo apt-get install vagrant

echo -e "\nInitializing Vagrant\n"
sudo vagrant init hashicorp/precise64
