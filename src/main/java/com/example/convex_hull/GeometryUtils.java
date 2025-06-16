package com.example.convex_hull; 

import java.util.List;

/**
 * GeometryUtils sınıfı, geometri ile ilgili yardımcı statik metotlar içerir.
 */
public class GeometryUtils {

    /**
     * Üç nokta (p, q, r) için çapraz çarpımı (cross product) hesaplar.
     * Bu değer, p-q-r noktalarının yönünü belirlemek için kullanılır.
     * Pozitifse sola dönüş, negatifse sağa dönüş, sıfırsa kolinear anlamına gelir.
     *
     * @param p Birinci nokta
     * @param q İkinci nokta
     * @param r Üçüncü nokta
     * @return Çapraz çarpım sonucu
     */
    public static double crossProduct(Point p, Point q, Point r) {
        return (q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x);

        //cross = (x₂ - x₁) * (y₃ - y₂) - (y₂ - y₁) * (x₃ - x₂)
    }

    /**
     * İki nokta (p1, p2) arasındaki ÖKLİD uzaklığının karesini hesaplar.
     * Karekök alınmadığı için daha hızlıdır ve karşılaştırmalarda yeterlidir.
     *
     * @param p1 Birinci nokta
     * @param p2 İkinci nokta
     * @return Uzaklığın karesi
     */
    public static double distanceSq(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    /**
     * Noktalar listesindeki en sol ve en aşağıda olan noktayı bulur.
     * Önce x koordinatına, eşitlik durumunda y koordinatına bakar.
     *
     * @param points Noktalar listesi
     * @return En sol ve en aşağıdaki nokta, ya da liste boşsa null
     */
    public static Point findLeftmostLowestPoint(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return null;
        }
        Point leftmost = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point current = points.get(i);
                // Daha küçük x varsa güncelle
            if (current.x < leftmost.x) {
                leftmost = current;
            // x eşitse, daha küçük y varsa güncelle
            } else if (current.x == leftmost.x) {
                if (current.y < leftmost.y) {
                    leftmost = current;
                }
            }
        }
        return leftmost;
    }
}
