#!/bin/bash

function prettyPrint() {
  printf -- "\n   -------------------------------------------------------------\n"
  printf "    %s \n" "$1"
  printf -- "   -------------------------------------------------------------\n\n"
}

mvn clean install
if [ $? != 0 ]; then
  prettyPrint "mvn clean install FAILED"
  exit 1
fi
cd core || {
  prettyPrint "No 'core' folder present"
  exit 1
}
mvn spring-boot:run
cd ..
