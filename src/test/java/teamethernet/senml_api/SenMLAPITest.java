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

	/* ENCODE JSON */
	@Test
	public void json_encode_empty() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		assertEquals("[]", senMLAPI.endSenML());
	}

    @Test
    public void json_encode_many_parameters() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.0,\"vb\":false,\"u\":\"dB\"}]", senMLAPI.endSenML());
    }

    @Test
    public void json_encode_multiple_records() throws JsonProcessingException, IOException {
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

   	/* ENCODE CBOR */

	@Test
	public void cbor_encode_empty() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		assertEquals("80", senMLAPI.endSenML());
	}

	@Test
	public void cbor_encode_many_parameters() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF", senMLAPI.endSenML());
	}

	@Test
	public void cbor_encode_multiple_records() throws JsonProcessingException, IOException {
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

	/* DECODE JSON */

    @Test
    public void json_decode_small() throws JsonProcessingException, IOException {
		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}]";
		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
		String bn = senMLAPI.getRecord(Label.BASE_NAME, 0);
		double v = SenMLAPI.getRecord(Label.VALUE, 0);
		boolean vb = SenMLAPI.getRecord(Label.BOOLEAN_VALUE, 0);
		assertEquals("mac:urn:dev:3290", bn);
		assertEquals(30.0, v);
		assertEquals(false, vb);
    }

    @Test
    public void json_decode_multiple_records() throws JsonProcessingException, IOException {
		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}, {\"bn\": \"hello\", \"ut\": 0.01, \"bt\": 0.0, \"bu\": \"Watt\"}, {\"s\":3040.201}]";
		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
		String bn1 = senMLAPI.getRecord(Label.BASE_NAME, 0);
		String bn2 = senMLAPI.getRecord(Label.BASE_NAME, 1);
		double ut = senMLAPI.getRecord(Label.UPDATE_TIME, 1);
		double bt = senMLAPI.getRecord(Label.BASE_TIME, 1);
		double bu = senMLAPI.getRecord(Label.BASE_UNIT, 1);
		double s = senMLAPI.getRecord(Label.SUM, 2);
		assertEquals("mac:urn:dev:3290", bn1);
		assertEquals("hello", bn2);
		assertEquals(30.0, ut);
		assertEquals(0.0, bt);
		assertEquals("Watt", bu);
		assertEquals("3040.201", s);
    }

     @Test
     public void json_decode_get_all_labels() throws JsonProcessingException, IOException {
 		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}, {\"bn\": \"hello\", \"ut\": 0.01, \"bt\": 0.0, \"bu\": \"Watt\"}, {\"s\":3040.201}]";
 		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
 		List labels0 = senMLAPI.getLabels(0);
 		List labels1 = senMLAPI.getLabels(1);
 		List labels2 = senMLAPI.getLabels(2);
 		List<Label> labels0expected = Arrays.asList(BASE_NAME, VALUE, BOOLEAN_VALUE);
 		List<Label> labels1expected = Arrays.asList(BASE_NAME, UPDATE_TIME, BASE_TIME, BASE_UNIT);
 		List<Label> labels2expected = Arrays.asList(SUM);
 		assertEquals(labels0expected, labels0);
 		assertEquals(labels1expected, labels1);
 		assertEquals(labels2expected, labels2);
    }

    @Test
    public void json_add_and_get_value() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode("");
		senMLAPI.addRecord(new Pair<>(Label.VALUE, 30.00),new Pair<>(Label.BASE_NAME, "hello1"));
		senMLAPI.addRecord(new Pair<>(Label.VALUE, 20.00),new Pair<>(Label.BASE_NAME, "hello2"));
		double v1 = senMLAPI.getRecord(Label.VALUE, 0);
		double v2 = senMLAPI.getRecord(Label.VALUE, 1);
		String bn1 = senMLAPI.getRecord(Label.BASE_NAME, 0);
		String bn2 = senMLAPI.getRecord(Label.BASE_NAME, 1);
		assertEquals(30.0, v1);
		assertEquals(20.0, v2);
		assertEquals("hello1", bn1);
		assertEquals("hello2", bn2);
	}

    @Test
    public void cbor_decode() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborDecode("81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF".getBytes());
		String bn = senMLAPI.getRecord(Label.BASE_NAME, 0);
		boolean vb = senMLAPI.getRecord(Label.BOOLEAN_VALUE, 0);
		assertEquals("mac:urn:dev:3290329032", bn);
		assertEquals(false, vb);
	}

    @Test
    public void cbor_decode_multiple_records() throws JsonProcessingException, IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborDecode("82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF".getBytes());
		String bn = senMLAPI.getRecord(Label.BASE_NAME, 1);
		assertEquals("mac:urn:dev:329032942", bn);
	}

}