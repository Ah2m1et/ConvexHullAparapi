package com.example.convex_hull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Aparapi ile Sıralı Poligonun Konvekslik Kontrolü ---");

        // --- Test 1: Gerçek bir konveks poligon (kare) ---
        List<Point> convexSquare = Arrays.asList(
            new Point(0, 0),
            new Point(100, 0),
            new Point(100, 100),
            new Point(0, 100)
        );
        System.out.println("\n--- Test 1: Konveks Kare (" + convexSquare.size() + " nokta) ---");
        testConvexity(convexSquare);

        // --- Test 2: Gerçek bir konkav poligon (yıldız gibi) ---
        List<Point> concaveStar = Arrays.asList(
            new Point(0, 0),
            new Point(50, 20), // İçbükey nokta
            new Point(100, 0),
            new Point(80, 50),
            new Point(100, 100),
            new Point(50, 80), // İçbükey nokta
            new Point(0, 100),
            new Point(20, 50)
        );
        System.out.println("\n--- Test 2: Konkav Yıldız (" + concaveStar.size() + " nokta) ---");
        testConvexity(concaveStar);

        // --- Test 3: Büyük sayılarda rastgele nokta (genellikle konkav) ---
        // Bu noktaların sıralı bir poligon oluşturduğunu varsayıyoruz
        List<Point> randomPolygon = createRandomPolygon(1000); // 1000 rastgele köşeli poligon
        System.out.println("\n--- Test 3: Rastgele Poligon (" + randomPolygon.size() + " nokta) ---");
        testConvexity(randomPolygon);

        // --- Performans Karşılaştırması (Büyük Sayıda Köşeli Poligon) ---
        System.out.println("\n--- Performans Karşılaştırması (100.000 Köşeli Poligon) ---");
        List<Point> largePolygon = createRandomPolygon(100_000); // Çok büyük rastgele köşeli poligon
        
        long startTimeAparapi = System.nanoTime(); // Zaman ölçümü başlangıcı
        ConvexHullAparapi aparapiChecker = new ConvexHullAparapi();
        boolean isConvexAparapi = aparapiChecker.isConvexPolygon(largePolygon);
        long endTimeAparapi = System.nanoTime(); // Zaman ölçümü bitişi
        long durationAparapi = TimeUnit.NANOSECONDS.toMillis(endTimeAparapi - startTimeAparapi);
        System.out.println("Aparapi Sonuç: Konveks mi? " + isConvexAparapi + ", Süre: " + durationAparapi + " ms");
    }

    /**
     * Verilen nokta listesinin konveksliğini test eder ve sonucu ekrana yazar.
     * @param points Test edilecek poligonun köşe noktaları
     */
    private static void testConvexity(List<Point> points) {
        ConvexHullAparapi checker = new ConvexHullAparapi();
        long startTime = System.nanoTime(); // Zaman ölçümü başlangıcı
        boolean isConvex = checker.isConvexPolygon(points); // Konvekslik kontrolü
        long endTime = System.nanoTime(); // Zaman ölçümü bitişi
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        System.out.println("Konveks mi? " + isConvex + ", Süre: " + duration + " ms");
    }

    /**
     * Rastgele sıralı poligon oluşturur (köşeleri birbirine bağlı).
     * Bu metodun ürettiği poligonlar genellikle konkav olacaktır.
     * @param numVertices Poligonun köşe sayısı
     * @return Rastgele oluşturulmuş köşe noktaları listesi
     */
    private static List<Point> createRandomPolygon(int numVertices) {
        List<Point> points = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numVertices; i++) {
            // Rastgelelik aralığını belirle
            double x = rand.nextDouble() * 1000; // Geniş bir alanda x
            double y = rand.nextDouble() * 1000; // Geniş bir alanda y
            points.add(new Point(x, y));
        }
        // Not: Bu sadece rastgele noktalar ekler, gerçekten kesişmeyen veya
        // kendini kesmeyen bir poligon garanti etmez. Ancak konvekslik kontrolü için yeterli olabilir.
        // Daha karmaşık rastgele poligonlar için "simple polygon generation" algoritmaları vardır.
        return points;
    }
}
