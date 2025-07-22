package upvictoria.pm_may_ago_2025.iti_271415.pi1u3.guerrero_guerrero;


import android.graphics.Rect;
import android.util.Log;
import android.widget.TextView;
import androidx.camera.core.ImageProxy;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import java.util.regex.*;
import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;

public class TextRecognitionProcessor implements ImageAnalysis.Analyzer {

    private final TextRecognizer recognizer;
    private final TextView resultView;

    public TextRecognitionProcessor(TextRecognizer recognizer, TextView resultView) {
        this.recognizer = recognizer;
        this.resultView = resultView;
    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        @androidx.camera.core.ExperimentalGetImage
        android.media.Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            recognizer.process(image)
                    .addOnSuccessListener(result -> {
                        for (Text.TextBlock block : result.getTextBlocks()) {
                            String text = block.getText();
                            if (text.matches(".*\\d{4} ?\\d{4} ?\\d{4} ?\\d{4}.*")) {
                                resultView.setText("Número detectado: " + text);
                                break;
                            }
                        }
                    })
                    .addOnFailureListener(Throwable::printStackTrace)
                    .addOnCompleteListener(task -> imageProxy.close());
        } else {
            imageProxy.close();
        }
    }
}
