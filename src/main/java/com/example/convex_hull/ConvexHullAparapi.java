package com.example.convex_hull;

import com.aparapi.Range;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConvexHullAparapi {

    /**
     * Verilen sıralı nokta listesinin konveks bir poligon oluşturup oluşturmadığını kontrol eder.
     * Bu, her köşe üçlüsü için çapraz çarpım işaretlerinin tutarlılığına bakılarak yapılır.
     * @param points Sıralı nokta listesi (poligonun köşeleri)
     * @return Konveks ise true, değilse false
     */
    public boolean isConvexPolygon(List<Point> points) {
        // 2 veya daha az nokta her zaman konvekstir (bir çizgi veya nokta)
        if (points == null || points.size() < 3) {
            return true;
        }

        // Noktaları Aparapi'ye uygun ilkel dizilere dönüştür
        float[] inX = new float[points.size()];
        float[] inY = new float[points.size()];
        for (int i = 0; i < points.size(); i++) {
            inX[i] = (float) points.get(i).x; // x koordinatlarını ata
            inY[i] = (float) points.get(i).y; // y koordinatlarını ata
        }

        // Aparapi Kernel'ini oluştur ve çalıştır
        NextPointKernel kernel = new NextPointKernel(inX, inY);
        
        // Çalışma aralığı tüm nokta sayısı kadar (her köşe için bir GPU thread'i)
        Range range = Range.create(points.size()); // N adet köşe var, N adet üçlü kontrol edilecek

        // Kernel çalışma süresini ölçmek için zaman al
        long kernelStartTime = System.nanoTime();
        kernel.execute(range); // GPU'da kernel'i çalıştır
        long kernelEndTime = System.nanoTime();
        System.out.println("  Kernel Çalışma Süresi: " + TimeUnit.NANOSECONDS.toMillis(kernelEndTime - kernelStartTime) + " ms");
        
        // Kernel kaynaklarını serbest bırak
        kernel.dispose(); 

        // GPU'dan gelen çapraz çarpım sonuçlarını oku
        float[] crossProducts = kernel.outCrossProducts;

        // CPU üzerinde tüm çapraz çarpım işaretlerinin tutarlı olup olmadığını kontrol et
        boolean firstSignDetermined = false; // İlk işaret belirlendi mi?
        int sign = 0; // 0: belirlenmedi, 1: pozitif, -1: negatif

        for (int i = 0; i < points.size(); i++) {
            float cp = crossProducts[i]; // İlgili çapraz çarpım sonucu

            if (cp == 0) {
                continue; // Doğrusal noktaları atla, konvekslik için sorun yaratmaz
            }

            if (!firstSignDetermined) {
                // İlk işareti belirle
                sign = (cp > 0) ? 1 : -1;
                firstSignDetermined = true;
            } else {
                // Sonraki işaretler ilk işaretle tutarlı mı kontrol et
                int currentSign = (cp > 0) ? 1 : -1;
                if (currentSign != sign) {
                    return false; // İşaret değişti, poligon konkav
                }
            }
        }

        return true; // Tüm işaretler tutarlı, poligon konveks
    }
}
