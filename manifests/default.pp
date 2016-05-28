  group { "puppet":
    ensure => "present",
  }

  exec { "apt_update":
    command => "apt-get update",
    path    => "/usr/bin",
  }

  package {"tomcat7":
	ensure => installed,
  }
	
  service {"tomcat7":
	ensure => running,
	enable => true,
	require => Package["tomcat7"],
  }	

