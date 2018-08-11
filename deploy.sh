#!/bin/bash
mkdir site 2>/dev/null
sed 's#\./target/scala-2\.12/chameleon-fastopt\.js#chameleon.js#' < index.html > site/index.html
grep -v '^//# sourceMappingURL=' < target/scala-2.12/chameleon-opt.js > site/chameleon.js
