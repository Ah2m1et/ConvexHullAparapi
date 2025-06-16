package com.example.convex_hull;

/**
 * Point sınıfı, 2 boyutlu düzlemde bir noktayı temsil eder.
 * x ve y koordinatları değiştirilemez (final).
 */
public class Point {
    // Noktanın x koordinatı
    public final double x;
    // Noktanın y koordinatı
    public final double y;

    /**
     * Point sınıfının yapıcı metodu.
     * @param x x koordinatı
     * @param y y koordinatı
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Noktanın (x, y) biçiminde string gösterimini döndürür.
     * @return Noktanın string gösterimi
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * İki noktanın eşit olup olmadığını kontrol eder.
     * @param obj Karşılaştırılacak nesne
     * @return Eşitse true, değilse false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Aynı nesne ise true
        if (obj == null || getClass() != obj.getClass()) return false; // Farklı tipteyse false
        Point point = (Point) obj;
        // x ve y koordinatları eşitse true
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    /**
     * Noktanın hash kodunu döndürür.
     * Hash tabanlı koleksiyonlarda kullanılmak için gereklidir.
     * @return Hash kodu
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}