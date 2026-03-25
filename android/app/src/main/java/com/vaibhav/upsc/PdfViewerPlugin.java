package com.vaibhav.upsc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import androidx.core.content.FileProvider;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.File;
import java.io.FileOutputStream;

@CapacitorPlugin(name = "PdfViewer")
public class PdfViewerPlugin extends Plugin {

    @PluginMethod
    public void openPdf(PluginCall call) {
        String base64Data = call.getString("base64");
        String fileName = call.getString("fileName", "document.pdf");

        if (base64Data == null) {
            call.reject("No PDF data provided");
            return;
        }

        try {
            // Remove data URL prefix if present
            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }

            // Decode base64 to bytes
            byte[] pdfBytes = Base64.decode(base64Data, Base64.DEFAULT);

            // Save to cache directory
            Activity activity = getActivity();
            File cacheDir = activity.getCacheDir();
            File pdfFile = new File(cacheDir, fileName);
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfBytes);
            fos.close();

            // Get URI using FileProvider
            Uri pdfUri = FileProvider.getUriForFile(
                activity,
                activity.getPackageName() + ".fileprovider",
                pdfFile
            );

            // Open with any PDF viewer app on phone
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Check if any PDF app is installed
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
                JSObject result = new JSObject();
                result.put("success", true);
                call.resolve(result);
            } else {
                // No PDF app - try browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, pdfUri);
                browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                activity.startActivity(browserIntent);
                JSObject result = new JSObject();
                result.put("success", true);
                result.put("message", "opened in browser");
                call.resolve(result);
            }

        } catch (Exception e) {
            call.reject("Failed to open PDF: " + e.getMessage());
        }
    }
}
