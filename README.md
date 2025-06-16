# ConvexHullAparapi

ConvexHullAparapi, Java ve [Aparapi](https://github.com/Syncleus/aparapi) kütüphanesini kullanarak nokta kümeleri üzerinde hızlı ve paralel **çevrel çökertme (convex hull)** algoritması uygulayan bir projedir. GPU hızlandırması sayesinde büyük veri setlerinde yüksek performans sağlar.

## Özellikler

- **Paralel İşleme:** Aparapi ile GPU üzerinde paralel hesaplama desteği.
- **Çevrel Çökertme Algoritması:** Graham Scan veya QuickHull gibi algoritmalarla çevrel çökertme.
- **Yüksek Performans:** Büyük nokta kümelerinde hızlı sonuçlar.
- **Kolay Kullanım:** Basit arayüz ve örnek kullanım kodları.

## Gereksinimler

- Java 8 veya üzeri
- Aparapi kütüphanesi
- Uyumlu bir GPU (OpenCL desteğiyle)

## Kurulum

1. Depoyu klonlayın:
    ```bash
    git clone https://github.com/ah2m1et/ConvexHullAparapi.git
    cd ConvexHullAparapi
    ```
2. Gerekli bağımlılıkları yükleyin (ör. Maven veya Gradle ile).
3. Projeyi derleyin:
    ```bash
    mvn clean install
    ```

## Kullanım

Aşağıda temel bir kullanım örneği verilmiştir:

```java
import com.example.ConvexHullAparapi;

public class Main {
     public static void main(String[] args) {
          float[] points = {/* x1, y1, x2, y2, ... */};
          ConvexHullAparapi hull = new ConvexHullAparapi();
          int[] hullIndices = hull.computeConvexHull(points);
          // hullIndices: çevrel çökertme noktalarının indeksleri
     }
}
```

## Katkıda Bulunma

Katkılarınızı bekliyoruz! Lütfen önce bir issue açarak önerinizi paylaşın ve ardından bir pull request gönderin.

## Lisans

Bu proje MIT lisansı ile lisanslanmıştır. Ayrıntılar için `LICENSE` dosyasına bakınız.