#!/bin/bash

sed 's#\./target/scala-2\.12/chameleon-fastopt\.js#chameleon.js#' < index.html > target/chameleon.html
grep -v '^//# sourceMappingURL=' < target/scala-2.12/chameleon-opt.js > target/chameleon.js
scp target/chameleon.js hal-9000:/var/www/tmp/chameleon.js
scp target/chameleon.html hal-9000:/var/www/tmp/chameleon.html
ssh hal-9000 "cd /var/www/tmp/; /bin/bash secret.sh"
