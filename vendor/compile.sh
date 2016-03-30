[ -d /bin ] && rmdir /bin
mkdir bin

cd aft-0.95
make all
mv eaf ../bin/eaf
make clean
cd ..

cd hv-1.3-src
make all
mv hv ../bin/hv
make clean
cd ..
