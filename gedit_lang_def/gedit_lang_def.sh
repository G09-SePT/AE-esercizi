#!/bin/sh

mkdir -p ~/.local/share/gtksourceview-3.0/language-specs
mkdir -p ~/.local/share/gtksourceview-3.0/styles/
cp ./jvm.lang ~/.local/share/gtksourceview-3.0/language-specs/
cp ./classic.xml ~/.local/share/gtksourceview-3.0/styles/
echo ""
echo "This script overrides the default classic.xml for current user. To improve it, change it from ~/.local/share/gtksourceview-3.0/styles/classic.xml"
return 0

