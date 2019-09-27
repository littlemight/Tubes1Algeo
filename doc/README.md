# Sistem Persamaan Linier, Determinan, dan Aplikasinya
Program ini dibuat untuk menyelesaikan Tugas Besar 1 mata kuliah Aljabar Linier & Geometri (IF2123)

Kelompok Jun From Korea:
- Jun Ho Choi Hedyatmo (13518044)
- Muhammad Kamal Shafi (13518113)
- Michel Fang          (13518137)


## Deskripsi Program
Program ini dibuat sesuai spesifikasi Tugas Besar 1. Dengan beberapa tambahan tertentu, Program ini dapat menerima input dari keyboard atau file, lalu dapat menampilkan hasil ke layar atau menyimpan ke file. Lalu, program ini dapat melakukan banyak operasi pada matriks, menyelesaikan sistem persamaan linier, juga membuat interpolasi fungsi dari titik-titik yang dimasukkan.


## Menjalankan Program
Program ini memerlukan instalasi Java Development Kit. Yang dapat di download dan install di https://www.oracle.com/technetwork/java/javase/downloads/index.html
lalu untuk menjalankan program ini dapat dengan melakukan download pada repository ini, lalu buka foldernya. setelah itu bisa dijalankan shell script yang sudah disiapkan yaitu compileScript.sh dan runDriver.sh di bash.

### Kompilasi
```
D:\Kuliah\Aljabar Linier & Geometri\Tubes1Algeo >
// folder yang dimaksud adalah folder yang mengandung folder src, bin, dan doc
D:\Kuliah\Aljabar Linier & Geometri\Tubes1Algeo >
# ./compileScript.sh
// atau jika tidak bisa
# compileScript.sh
// atau juga dapat menggunakan
# javac -d ./bin/ ./src/app/*.java
// jika berhasil dijalankan tanpa pesan kesalahan, maka program berhasil dikompilasi
```

### Eksekusi
```
D:\Kuliah\Aljabar Linier & Geometri\Tubes1Algeo >
// folder yang dimaksud adalah folder yang mengandung folder src, bin, dan doc
D:\Kuliah\Aljabar Linier & Geometri\Tubes1Algeo >
# ./runDriver.sh
// atau jika tidak bisa
# runDriver.sh
// atau juga dapat menggunakan
# java -cp ./bin app.Driver
// jika berhasil dijalankan tanpa pesan kesalahan, maka program berhasil dijalankan
```

## Petunjuk Penggunaan
Terdapat delapan perintah utama yaitu:
1. Sistem Persamaan Linier
2. Determinan Matriks
3. Matriks Balikan
4. Matriks Kofaktor
5. Matriks Adjoin
6. Interpolasi Polinom
7. Studi Kasus
8. Keluar

### Sistem Persamaan Linier
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput matriks maka anda akan diberi beberapa opsi metode untuk menyelesaikan sistem persamaan linier tersebut.
1. Metode eliminasi Gauss
2. Metode eliminasi Gauss-Jordan
3. Metode matriks balikan
4. Kaidah Cramer
masing-masing akan menyelesaikan sistem persamaan linier namun dengan metode yang berbeda-beda.

### Determinan Matriks
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput matriks maka anda akan diberi beberapa opsi metode untuk mencari determinan dari matriks.
1. Metode eliminasi Gauss
2. Metode eliminasi Gauss-Jordan
3. Metode Kofaktor

### Matriks Balikan
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput matriks maka anda akan diberi beberapa opsi metode untuk mencari matriks balikan.
1. Metode eliminasi Gauss-Jordan
2. Metode Adjoin

### Matriks Kofaktor
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput matriks maka akan ditampilkan matriks kofaktornya.

### Matriks Adjoin
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput matriks maka akan ditampilkan matriks adjoinnya.

### Interpolasi Polinom
Ketika memilih pilihan ini, anda akan diberikan dua pilihan yaitu ingin menginput dari keyboard atau dari file. setelah memilih dan menginput kumpulan titik-titik, maka akan dilakukan interpolasi untuk mencari fungsi hasil interpolasi.

### Uji Studi Kasus Dengan Kakas Online
1_1
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,1,-1,-1,1%7D,%7B2,5,-7,-5,-2%7D,%7B2,-1,1,3,4%7D,%7B5,2,-4,2,6%7D%7D%29

1_2
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,-1,0,0,1,3%7D,%7B1,1,0,-3,0,6%7D,%7B2,-1,0,1,-1,5%7D,%7B-1,2,0,-2,-1,-1%7D%7D%29

1_3http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B0,1,0,0,1,0,2%7D,%7B0,0,0,1,1,0,-1%7D,%7B0,1,0,0,0,1,1%7D%7D%29

1_H6
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,1/2,3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,1%7D,%7B1/2,3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,0%7D,%7B3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,0%7D,%7B1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,0%7D,%7B1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,0%7D,%7B8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,0%7D%7D%29

1_H10
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,1/2,3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,1%7D,%7B1/2,3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,0%7D,%7B3333333333333333/10000000000000000,1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,0%7D,%7B1/4,1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,0%7D,%7B1/5,8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,0%7D,%7B8333333333333333/50000000000000000,2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,6666666666666667/100000000000000000,0%7D,%7B2857142857142857/20000000000000000,1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,6666666666666667/100000000000000000,1/16,0%7D,%7B1/8,1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,6666666666666667/100000000000000000,1/16,11764705882352941/200000000000000000,0%7D,%7B1111111111111111/10000000000000000,1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,6666666666666667/100000000000000000,1/16,11764705882352941/200000000000000000,1111111111111111/20000000000000000,0%7D,%7B1/10,9090909090909091/100000000000000000,8333333333333333/100000000000000000,7692307692307693/100000000000000000,3571428571428571/50000000000000000,6666666666666667/100000000000000000,1/16,11764705882352941/200000000000000000,1111111111111111/20000000000000000,2631578947368421/50000000000000000,0%7D%7D%29

2_1
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,-1,2,-1,-1%7D,%7B2,1,-2,-2,-2%7D,%7B-1,2,-4,1,1%7D,%7B3,0,0,-3,-3%7D%7D%29

2_2
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B2,0,8,0,8%7D,%7B0,1,0,4,6%7D,%7B-4,0,6,0,6%7D,%7B0,-2,0,3,-1%7D,%7B2,0,-4,0,-4%7D,%7B0,1,0,-2,0%7D%7D%29

2_i
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B8,1,3,2,0%7D,%7B2,9,-1,-2,1%7D,%7B1,3,2,-1,2%7D,%7B1,0,6,4,3%7D%7D%29

2_ii
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B0,0,0,0,0,0,1,1,1,13%7D,%7B0,0,0,1,1,1,0,0,0,15%7D,%7B1,1,1,0,0,0,0,0,0,8%7D,%7B0,0,4289/100000,0,4289/100000,3/4,4289/100000,3/4,15349/250000,1479/100%7D,%7B0,1/4,91421/100000,1/4,91421/100000,1/4,91421/100000,1/4,0,1431/100%7D,%7B15349/25000,3/4,4289/100000,3/4,4289/100000,0,4289/100000,0,0,381/100%7D,%7B0,0,1,0,0,1,0,0,1,18%7D,%7B0,1,0,0,1,0,0,1,0,12%7D,%7B1,0,0,1,0,0,1,0,0,6%7D,%7B4289/100000,3/4,15349/25000,0,4289/100000,3/4,0,0,4289/100000,1051/100%7D,%7B91421/100000,1/4,0,1/4,91421/100000,1/4,0,1/4,91421/100000,1613/100%7D,%7B4289/100000,0,0,3/4,4289/100000,0,15349/25000,3/4,4289/100000,176/25%7D%7D%29

3_5x5
http://matrixcalc.org/en/det.html#determinant-Gauss%28%7B%7B89,89,27,23,93%7D,%7B15,73,52,25,78%7D,%7B59,47,68,78,17%7D,%7B89,18,78,72,42%7D,%7B20,37,68,51,32%7D%7D%29

3_10x10
http://matrixcalc.org/en/det.html#determinant-Gauss%28%7B%7B38,90,85,83,50,71,77,69,32,31%7D,%7B85,17,66,44,55,69,35,24,75,57%7D,%7B99,99,43,12,83,100,64,97,77,76%7D,%7B71,69,59,20,61,99,68,86,43,40%7D,%7B79,25,52,38,91,66,14,88,34,87%7D,%7B51,71,10,75,35,66,100,43,61,12%7D,%7B33,30,32,33,90,22,22,59,11,41%7D,%7B94,54,92,40,23,86,86,90,18,51%7D,%7B34,96,54,64,95,19,72,92,85,55%7D,%7B72,53,15,83,90,11,50,16,68,64%7D%7D%29

4_Rangkaian
http://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,1,1,0,0,0,0,0,0,0,0%7D,%7B0,-1,0,1,-1,0,0,0,0,0,0%7D,%7B0,0,-1,0,0,1,0,0,0,0,0%7D,%7B0,0,0,0,1,-1,0,0,0,0,0%7D,%7B5,-10,0,-20,0,0,0,0,0,0,200%7D,%7B0,-10,10,0,15,5,0,0,0,0,0%7D,%7B0,0,0,20,0,0,0,0,0,1,0%7D,%7B0,0,0,0,15,0,0,0,1,-1,0%7D,%7B0,0,0,0,0,5,0,1,-1,0,0%7D,%7B0,0,10,0,0,0,1,-1,0,0,0%7D%7D%29

5_Penduduk
https://matrixcalc.org/en/slu.html#solve-using-Gauss-Jordan-elimination%28%7B%7B1,1971,3884841,7657021611,15091989595281,29746311492298851,58629979951321035321,115559690484053760617691,108/5%7D,%7B1,1980,3920400,7762392000,15369536160000,30431681596800000,60254729561664000000,119304364532094720000000,137/5%7D,%7B1,1990,3960100,7880599000,15682392010000,31207960099900000,62103840598801000000,123586642791613990000000,177/5%7D,%7B1,1995,3980025,7940149875,15840599000625,31601995006246875,63045980037462515625,125776730174737718671875,196/5%7D,%7B1,2000,4000000,8000000000,16000000000000,32000000000000000,64000000000000000000,128000000000000000000000,257/10%7D,%7B1,2010,4040100,8120601000,16322408010000,32808040100100000,65944160601201000000,132547762808414010000000,216/5%7D,%7B1,2015,4060225,8181353375,16485427050625,33218135507009375,66934543046623890625,134873104238947139609375,467/10%7D,%7B1,2019,4076361,8230172859,16616719002321,33549155665686099,67735745289020233881,136758469738531852205739,491/10%7D%7D%29

Menyederhanakan fungsi. 
https://www.desmos.com/calculator/6fxrm2aqjw

### Keluar
pilihan ini untuk keluar dari program
