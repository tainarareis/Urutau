#Executa o update no sistema
exec { "apt_update":
  command => "apt-get update",
  path    => "/usr/bin",
}

#Instalacao das dependencias
package { [
  'openjdk-7-jdk',
  'git',
  'tomcat7',
  'maven',
]:
  ensure => present,
  require => Exec["apt_update"],
}

#Configuracao do banco de dados

class { 'postgresql::server': }

postgresql::server::db { 'urutau' :
    user => 'urutau',
    password => postgresql_password('urutau', 'urutau'),

}

postgresql::server::role { 'urutau' :
    password_hash => postgresql_password('urutau', 'urutau'),

}

postgresql::server::database_grant { 'permission' :
    privilege => 'ALL',
    db => 'urutau',
    role => 'urutau',
}
