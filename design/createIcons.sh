mkdir tmp
inkscape -w 16 -h 16 -o tmp/16.png icon.svg
inkscape -w 32 -h 32 -o tmp/32.png icon.svg
inkscape -w 48 -h 48 -o tmp/48.png icon.svg
inkscape -w 128 -h 128 -o tmp/128.png icon.svg
inkscape -w 256 -h 256 -o tmp/256.png icon.svg
inkscape -w 512 -h 512 -o tmp/512.png icon.svg
inkscape -w 1024 -h 1024 -o tmp/1024.png icon.svg


convert tmp/16.png tmp/32.png tmp/48.png tmp/128.png tmp/256.png tmp/512.png ../composeApp/desktopAppIcons/WindowsIcon.ico
cp tmp/512.png ../composeApp/desktopAppIcons/LinuxIcon.png
png2icns ../composeApp/desktopAppIcons/MacosIcon.icns tmp/16.png tmp/32.png tmp/128.png tmp/256.png tmp/512.png tmp/1024.png


rm tmp -r