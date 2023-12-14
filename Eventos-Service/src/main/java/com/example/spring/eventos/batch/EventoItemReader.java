package com.example.spring.eventos.batch;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.ClassPathResource;

import com.example.spring.eventos.response.EventoDTOCarga;
import com.opencsv.CSVReader;

public class EventoItemReader implements ItemReader<String[]> {

	private String file = "eventos.csv";
	
    private CSVReader csvReader;

    public EventoItemReader() {
    	try {
    		csvReader = new CSVReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

    }

    @Override
    public String[] read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (csvReader.getLinesRead() == 0) {
            csvReader.readNext();
        }
        
        String[] values = csvReader.readNext();

        if (values != null) {
            for (String string : values) {
                System.out.print(string + ",");
            }
        } else {
            System.out.println("Datos leídos correctamente");
        }
        System.out.println();

        return values;
    }
}