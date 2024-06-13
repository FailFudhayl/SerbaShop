package com.example.serbashop.datasource;

import com.example.serbashop.R;
import com.example.serbashop.model.News;

import java.util.ArrayList;

public class DataNews {
    public static ArrayList<News> newss = generateDummyNews();

    private static ArrayList<News> generateDummyNews() {
        ArrayList<News> newss1 = new ArrayList<>();
        newss1.add(new News("Sayuran Segar dan Berkualitas Kini Hadir di Toko Resmi Kami!",
                R.drawable.news_groceri,
                "Dapatkan berbagai macam sayuran segar dan berkualitas tinggi di toko resmi kami. Kami menyediakan berbagai jenis sayuran dari petani lokal terbaik, dengan harga yang terjangkau dan kualitas terjamin. Kunjungi toko kami sekarang dan rasakan manfaatnya!"
        ));
        newss1.add(new News("Temukan Kenyamanan Istimewa dengan Sofa Furniture Terbaru Kami!",
                R.drawable.news_furniture,
                "Tingkatkan kenyamanan ruang tamu Anda dengan sofa furniture terbaru dari kami. Didesain dengan gaya modern dan elegan, sofa kami terbuat dari bahan berkualitas tinggi yang tahan lama dan mudah dibersihkan. Nikmati pengalaman duduk yang nyaman dan rileks bersama keluarga dan teman."
        ));
        newss1.add(new News("Rasakan Kesegaran Lautan dengan Parfum Baru \"The Ocean\"!",
                R.drawable.news_parfume,
                "Wujudkan aroma segar dan menenangkan lautan di setiap momen Anda dengan parfum baru \"The Ocean\". Dibuat dengan perpaduan aroma laut yang menyegarkan dan bunga yang lembut, parfum ini cocok untuk digunakan pria maupun wanita. Dapatkan \"The Ocean\" sekarang dan rasakan sensasi kesegaran yang tak terlupakan!"
        ));
        newss1.add(new News("Rawat Kulit Wajah Anda dengan Serum Wajah Terbaru!",
                R.drawable.news_skincare,
                " Perkenalkan serum wajah terbaru kami yang diformulasikan khusus untuk membantu mencerahkan, melembabkan, dan menutrisi kulit wajah Anda. Dibuat dengan bahan-bahan alami yang aman dan efektif, serum ini cocok untuk semua jenis kulit. Dapatkan kulit wajah yang lebih sehat, cerah, dan bercahaya dengan serum wajah terbaru kami!"
        ));
        return newss1;
    }
}
