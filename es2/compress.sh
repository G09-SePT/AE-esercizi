#!/bin/sh

zip -v -T -FS -r "es2_GR09" jas/ mal/ gen_code/ src/ ijvm.conf EsempioTestSvolti.txt -x *.mic1 *.ijvm *.mne *.dbg CMakeLists.txt \*/.idea/\* \*/cmake-build-debug/\*

