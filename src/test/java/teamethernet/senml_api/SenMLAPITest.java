package teamethernet.senml_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SenMLAPITest {

    @Before
    public void setUp() throws Exception {
    }

	@Test
	public void json_empty() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		assertEquals("[]", senMLAPI.endSenML());
	}

    @Test
    public void json_many_parameters() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.0,\"vb\":false,\"u\":\"dB\"}]", senMLAPI.endSenML());
    }

    @Test
    public void json_multiple_records() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn1 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair bn2 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:329032942");
		Pair bver = new Pair<>(Label.BASE_VERSION, 0);
		Pair vs = new Pair<>(Label.STRING_VALUE, "hello");
		Pair ut = new Pair<>(Label.UPDATE_TIME, 30.00);

		senMLAPI.addRecord(bn1, bver);
		senMLAPI.addRecord(bn2, vs, ut);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"bver\":0},{\"bn\":\"mac:urn:dev:329032942\",\"vs\":\"hello\",\"ut\":30.0}]", senMLAPI.endSenML());
    }

	@Test
	public void cbor_empty() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		assertEquals("80", senMLAPI.endSenML());
	}

	@Test
	public void cbor_many_parameters() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF", senMLAPI.endSenML());
	}

	@Test
	public void cbor_multiple_records() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		Pair bn1 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair bn2 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:329032942");
		Pair bver = new Pair<>(Label.BASE_VERSION, 0);
		Pair vs = new Pair<>(Label.STRING_VALUE, "hello");
		Pair ut = new Pair<>(Label.UPDATE_TIME, 30.00);

		senMLAPI.addRecord(bn1, bver);
		senMLAPI.addRecord(bn2, vs, ut);
		assertEquals("82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF", senMLAPI.endSenML());
	}
}