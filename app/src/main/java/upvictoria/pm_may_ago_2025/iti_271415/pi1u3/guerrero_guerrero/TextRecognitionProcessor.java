package upvictoria.pm_may_ago_2025.iti_271415.pi1u3.guerrero_guerrero;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
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
                        String allText = result.getText();

                        // Detectar número de tarjeta
                        Pattern cardPattern = Pattern.compile("(\\d[ -]*?){13,16}");
                        Matcher cardMatcher = cardPattern.matcher(allText);
                        String cardNumber = "";
                        if (cardMatcher.find()) {
                            cardNumber = cardMatcher.group().replaceAll("[^\\d]", "").replaceAll("(.{4})(?!$)", "$1 ");
                        }

                        // Detectar fecha de caducidad
                        Pattern datePattern = Pattern.compile("(0[1-9]|1[0-2])/\\d{2,4}");
                        Matcher dateMatcher = datePattern.matcher(allText);
                        String expiryDate = "";
                        if (dateMatcher.find()) {
                            expiryDate = dateMatcher.group();
                        }

                        if (!cardNumber.isEmpty() || !expiryDate.isEmpty()) {
                            String resultText = "Número de tarjeta: " + cardNumber + "\nCaducidad: " + expiryDate;

                            // Mostrar en el TextView
                            resultView.setText(resultText);

                            // Crear ventana emergente
                            Context context = resultView.getContext();
                            new AlertDialog.Builder(context)
                                    .setTitle("Datos detectados")
                                    .setMessage(resultText)
                                    .setPositiveButton("Copiar", (dialog, which) -> {
                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("Datos detectados", resultText);
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(context, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("Cerrar", null)
                                    .show();
                        }
                    })
                    .addOnFailureListener(Throwable::printStackTrace)
                    .addOnCompleteListener(task -> imageProxy.close());
        } else {
            imageProxy.close();
        }
    }

    public void handleRecognizedText(Text result, Context context) {
        String allText = result.getText();

        Pattern cardPattern = Pattern.compile("(\\d[ -]*?){13,16}");
        Matcher cardMatcher = cardPattern.matcher(allText);
        String cardNumber = "";
        if (cardMatcher.find()) {
            cardNumber = cardMatcher.group().replaceAll("[^\\d]", "").replaceAll("(.{4})(?!$)", "$1 ");
        }

        Pattern datePattern = Pattern.compile("(0[1-9]|1[0-2])/\\d{2,4}");
        Matcher dateMatcher = datePattern.matcher(allText);
        String expiryDate = "";
        if (dateMatcher.find()) {
            expiryDate = dateMatcher.group();
        }

        if (!cardNumber.isEmpty() || !expiryDate.isEmpty()) {
            String resultText = "Número de tarjeta: " + cardNumber + "\nCaducidad: " + expiryDate;
            resultView.setText(resultText);

            new AlertDialog.Builder(context)
                    .setTitle("Datos detectados")
                    .setMessage(resultText)
                    .setPositiveButton("Copiar", (dialog, which) -> {
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Datos detectados", resultText);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cerrar", null)
                    .show();
        }
    }

}
