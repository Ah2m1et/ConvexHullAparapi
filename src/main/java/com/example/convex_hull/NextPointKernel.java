package com.example.convex_hull;

import com.aparapi.Kernel;

/**
 * NextPointKernel sınıfı, GPU üzerinde paralel olarak çapraz çarpım (cross product)
 * hesaplamak için Aparapi Kernel'ını kullanır.
 */
public class NextPointKernel extends Kernel {
    // Input: Tüm noktaların x koordinatları (float dizi)
    private final float[] inX;
    // Input: Tüm noktaların y koordinatları (float dizi)
    private final float[] inY;
    // Toplam nokta sayısı
    private final int numPoints;

    // Output: Her bir köşe üçlüsü için çapraz çarpım sonucu (float dizi)
    public float[] outCrossProducts;

    /**
     * Kernel yapıcı metodu. Noktaların x ve y koordinatlarını alır.
     * @param inX Noktaların x koordinatları
     * @param inY Noktaların y koordinatları
     */
    public NextPointKernel(float[] inX, float[] inY) {
        this.inX = inX;
        this.inY = inY;
        this.numPoints = inX.length;
        this.outCrossProducts = new float[numPoints]; // Her köşe için bir CP sonucu
    }

    /**
     * Üç nokta için çapraz çarpım (cross product) hesaplar.
     * float tipinde parametreler kullanılır.
     * @param p_x Birinci noktanın x'i
     * @param p_y Birinci noktanın y'si
     * @param q_x İkinci noktanın x'i
     * @param q_y İkinci noktanın y'si
     * @param r_x Üçüncü noktanın x'i
     * @param r_y Üçüncü noktanın y'si
     * @return Çapraz çarpım sonucu
     */
    private float crossProduct(float p_x, float p_y, float q_x, float q_y, float r_x, float r_y) {
        return (q_x - p_x) * (r_y - p_y) - (q_y - p_y) * (r_x - p_x);
    }

    /**
     * GPU üzerinde paralel olarak çalışacak ana metod.
     * Her iş parçası (thread), üç ardışık nokta için çapraz çarpım hesaplar.
     */
    @Override
    public void run() {
        int gid = getGlobalId(); // Mevcut GPU iş elemanının (thread) ID'si

        // Her gid, P_gid, P_{gid+1}, P_{gid+2} üçlüsünü temsil eder
        if (gid < numPoints) {
            // Mevcut noktanın koordinatları
            float p_x = inX[gid];
            float p_y = inY[gid];

            // Sonraki noktanın koordinatları (dizinin sonuna gelirse başa döner)
            float q_x = inX[(gid + 1) % numPoints];
            float q_y = inY[(gid + 1) % numPoints];

            // Bir sonraki noktanın koordinatları (dizinin sonuna gelirse başa döner)
            float r_x = inX[(gid + 2) % numPoints];
            float r_y = inY[(gid + 2) % numPoints];

            // Çapraz çarpımı hesapla ve çıktı dizisine yaz
            outCrossProducts[gid] = crossProduct(p_x, p_y, q_x, q_y, r_x, r_y);
        }
    }
}
