Hi Viewers

This is my github repo and if you want to run it on your local device follow these simple steps 
-> clone this file in your local and setup your intellij
-> simply kill the PID on port 8080 if exists and then run it locally 
->hit the curls atach via postman to find the output 


 Test Steps

1. Upload PDF → Get `fileId`
2. Search using `fileId`
3. Try one-shot search 


Structure and flow

src/
├── main/java/com/example/PdfReader/
│ ├── PdfReaderApplication.java 
│ ├── controller/
│ │ └── PdfController.java 
│ ├── pdfService/
│ │ ├── PdfService.java 
│ │ ├── PdfTextExtractor.java 
│ │ └── SearchService.java 
│ ├── model/
│ │ ├── DocumentData.java 
│ │ ├── Match.java 
│ │ ├── SearchResponse.java 
│ │ └── UploadResponse.java 
│ ├── exception/
│ │ ├── ApiException.java 
│ │ └── GlobalExceptionHandler.java 
│ └── pdfUtils/
│ └── FileValidators.java 
└── resources/
└── application.properties 


Work flow 

1. Upload API: PDF → Extract text by pages → Store in memory with UUID
2. Search API: Find text matches → Create snippets → Calculate scores → Sort results
3. Temp Storage : In-memory HashMap (UUID → DocumentData)->Concurrent hashmap is used



API COLLECTION

https://swxedcrftgbyhj.postman.co/workspace/swxedcrftgbyhj-Workspace~fb9381a2-2574-4d10-899f-0e7b0dd9013c/collection/32656590-34e54ae3-1fb3-4486-89c6-3e49aae73844?action=share&source=copy-link&creator=32656590


