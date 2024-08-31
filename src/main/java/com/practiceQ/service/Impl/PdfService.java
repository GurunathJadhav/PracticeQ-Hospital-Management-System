package com.practiceQ.service.Impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.practiceQ.payload.InvoiceDtoWithService;
import com.practiceQ.payload.PatientDto;
import com.practiceQ.payload.ServiceListForInvoice;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
public class PdfService {

    public boolean generatePdf(String filename, InvoiceDtoWithService invoiceDtoWithService) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Title Font
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.WHITE);
            Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

            // Title with background
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);
            PdfPCell titleCell = new PdfPCell(new Phrase("Invoice", titleFont));
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setPadding(10);
            titleCell.setBackgroundColor(new BaseColor(70, 130, 180)); // Steel blue background color
            titleCell.setBorder(PdfPCell.NO_BORDER);
            titleTable.addCell(titleCell);
            document.add(titleTable);
            document.add(Chunk.NEWLINE);

            // Invoice details with background
            PdfPTable invoiceTable = new PdfPTable(2);
            invoiceTable.setWidthPercentage(100);
            invoiceTable.setSpacingBefore(10f);
            invoiceTable.setSpacingAfter(10f);

            invoiceTable.addCell(getCell("Invoice ID:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            invoiceTable.addCell(getCell(invoiceDtoWithService.getInvoiceId(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));
            invoiceTable.addCell(getCell("Date:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            invoiceTable.addCell(getCell(invoiceDtoWithService.getDate(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));
            invoiceTable.addCell(getCell("Total Amount:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            invoiceTable.addCell(getCell("Rs. " + invoiceDtoWithService.getTotalAmount(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));

            document.add(invoiceTable);

            // Patient details with background
            PatientDto patient = invoiceDtoWithService.getPatientDto();

            PdfPTable patientTable = new PdfPTable(2);
            patientTable.setWidthPercentage(100);
            patientTable.setSpacingBefore(10f);
            patientTable.setSpacingAfter(10f);

            patientTable.addCell(getCell("Patient Name:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            patientTable.addCell(getCell(patient.getFirstName() + " " + patient.getLastName(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));
            patientTable.addCell(getCell("Patient ID:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            patientTable.addCell(getCell(patient.getPatientId(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));
            patientTable.addCell(getCell("Patient Email:", PdfPCell.ALIGN_LEFT, font, BaseColor.LIGHT_GRAY));
            patientTable.addCell(getCell(patient.getEmail(), PdfPCell.ALIGN_LEFT, font, BaseColor.WHITE));

            document.add(patientTable);

            // Service details with different background and header styling
            PdfPTable serviceTable = new PdfPTable(new float[]{1, 2, 1}); // Adjusted column widths
            serviceTable.setWidthPercentage(100);
            serviceTable.setSpacingBefore(10f);
            serviceTable.setSpacingAfter(10f);

            serviceTable.addCell(getHeaderCell("Service ID", headerFont, new BaseColor(70, 130, 180)));
            serviceTable.addCell(getHeaderCell("Service Name", headerFont, new BaseColor(70, 130, 180)));
            serviceTable.addCell(getHeaderCell("Cost", headerFont, new BaseColor(70, 130, 180)));


            List<ServiceListForInvoice> services = invoiceDtoWithService.getServiceListForInvoices();
            for (ServiceListForInvoice service : services) {
                serviceTable.addCell(getCell(service.getServiceId(), PdfPCell.ALIGN_CENTER, font, BaseColor.WHITE));
                serviceTable.addCell(getCell(service.getServiceName(), PdfPCell.ALIGN_CENTER, font, BaseColor.WHITE));
                serviceTable.addCell(getCell("Rs. " + service.getCost(), PdfPCell.ALIGN_CENTER, font, BaseColor.WHITE));
            }

            PdfPCell footerCell = getHeaderCell("Total Cost", headerFont, new BaseColor(70, 130, 180));
            footerCell.setColspan(2); // Span across two columns
            serviceTable.addCell(footerCell);

            double totalCost = services.stream().mapToDouble(ServiceListForInvoice::getCost).sum();
            serviceTable.addCell(getCell("Rs. " + totalCost, PdfPCell.ALIGN_CENTER, font, new BaseColor(70, 130, 180)));
            document.add(serviceTable);

            document.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PdfPCell getCell(String text, int alignment, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBackgroundColor(backgroundColor);
        return cell;
    }

    private PdfPCell getHeaderCell(String text, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(backgroundColor);
        return cell;
    }
}
