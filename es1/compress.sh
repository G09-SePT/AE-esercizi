#!/bin/sh

zip -v -T -FS -r "es1_GR09" jas/ mal/ test_es1_mal/ ijvm.conf RelazioneEs1.pdf -x *.mic1 *.ijvm *.mne *.dbg CMakeLists.txt \*/.idea/\* \*/cmake-build-debug/\*

