package MyChatBot.ChatBotService.Controllers;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;



import MyChatBot.ChatBotService.ServiceLayer.AIConnectionService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {
	
	@Autowired
	private AIConnectionService AiService;
	
	
	
	//*********************************** Static Upload ********************************************
	@GetMapping("/send-pdf")
    public ResponseEntity<String> sendPdfToAi() {
        try {
            // Path to the static PDF file in resources/static folder
            String filePath = "src/main/resources/static/PDF1.pdf"; // Change to your PDF file path

            // Extract text content from the PDF
            String pdfText = extractTextFromPdf(filePath);


            // Call the AI model with the extracted text content
            String aiResponse = AiService.getAiResponse(pdfText);

            // Return the AI response
            return ResponseEntity.ok(aiResponse);
            
            
            
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing the PDF: " + e.getMessage());
        }
    }

    private String extractTextFromPdf(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }
	
    
    
    
	
	//************************************ dynamic upload *************************************
	// tryint to get response for dynamic file upload

    @PostMapping("/upload-pdf")
    public ResponseEntity<String> uploadPDF(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }
        try {
            // Process the uploaded PDF file
            String content = extractTextFromPDF(file);
            // You can perform any operations on the extracted content here
            String response = AiService.getAiResponse(content);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file.");
        }
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        // Extract text from PDF using a library like Apache PDFBox or iText
        // For example, using Apache PDFBox
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
    
 
}
