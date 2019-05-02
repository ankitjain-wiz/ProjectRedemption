package org.epo.cms.edfs.services.dossierpersistence.pojo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This class is used to serialize date from JSON.
 * @author kpandey
 *
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)

			throws IOException, JsonProcessingException {

		String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);

	}

}
