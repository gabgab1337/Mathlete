import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.broszke.mathlete.R

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_layout)

        val animationView = findViewById<LottieAnimationView>(R.id.animation_view)
        animationView.setAnimation("loading.json")
        animationView.playAnimation()
    }
}