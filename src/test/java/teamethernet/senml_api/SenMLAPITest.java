package teamethernet.senml_api;

import javafx.util.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SenMLAPITest {

	private static final double EPSILON = Math.ulp(1.0);

	/* ENCODE JSON */

	@Test
	public void json_encode_empty() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		assertEquals("[]", senMLAPI.endSenML());
	}

    @Test
    public void json_encode_many_parameters() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.0,\"vb\":false,\"u\":\"dB\"}]", senMLAPI.endSenML());
    }

    @Test
    public void json_encode_multiple_records() throws IOException {
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
	public void cbor_encode_empty() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		assertEquals("80", senMLAPI.endSenML());
	}

	@Test
	public void cbor_encode_many_parameters() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		senMLAPI.addRecord(bn, v, vb, u);
		assertEquals("81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF", senMLAPI.endSenML());
	}

	@Test
	public void cbor_encode_multiple_records() throws IOException {
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
    public void json_decode_small() throws IOException {
		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}]";
		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
		final String bn = (String) senMLAPI.getValue(Label.BASE_NAME, 0);
		double v = (double) senMLAPI.getValue(Label.VALUE, 0);
		boolean vb = (boolean) senMLAPI.getValue(Label.BOOLEAN_VALUE, 0);
		assertEquals("mac:urn:dev:3290", bn);
		assertEquals(30.0, v, EPSILON);
		assertFalse(vb);
    }

    @Test
    public void json_decode_multiple_records() throws IOException {
		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}, {\"bn\": \"hello\", \"ut\": 0.01, \"bt\": 0.0, \"bu\": \"Watt\"}, {\"s\":3040.201}]";
		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
		String bn1 = (String) senMLAPI.getValue(Label.BASE_NAME, 0);
		String bn2 = (String) senMLAPI.getValue(Label.BASE_NAME, 1);
		double ut = (double) senMLAPI.getValue(Label.UPDATE_TIME, 1);
		double bt = (double) senMLAPI.getValue(Label.BASE_TIME, 1);
		double s = (double) senMLAPI.getValue(Label.SUM, 2);
		String bu = (String) senMLAPI.getValue(Label.BASE_UNIT, 1);
		assertEquals("mac:urn:dev:3290", bn1);
		assertEquals("hello", bn2);
		assertEquals(0.01, ut, EPSILON);
		assertEquals(0.0, bt, EPSILON);
		assertEquals("Watt", bu);
		assertEquals(3040.201, s, EPSILON);
    }

     @Test
     public void json_decode_get_all_labels() throws IOException {
 		String inputjson = "[{\"bn\": \"mac:urn:dev:3290\", \"v\":30.0, \"vb\": false}, {\"bn\": \"hello\", \"ut\": 0.01, \"bt\": 0.0, \"bu\": \"Watt\"}, {\"s\":3040.201}]";
 		final SenMLAPI senMLAPI = SenMLAPI.initJsonDecode(inputjson);
 		List labels0 = senMLAPI.getLabels(0);
 		List labels1 = senMLAPI.getLabels(1);
 		List labels2 = senMLAPI.getLabels(2);
 		List<Label> labels0expected = Arrays.asList(Label.BASE_NAME, Label.VALUE, Label.BOOLEAN_VALUE);
 		List<Label> labels1expected = Arrays.asList(Label.BASE_NAME, Label.UPDATE_TIME, Label.BASE_TIME, Label.BASE_UNIT);
 		List<Label> labels2expected = Collections.singletonList(Label.SUM);
 		assertEquals(labels0expected, labels0);
 		assertEquals(labels1expected, labels1);
 		assertEquals(labels2expected, labels2);
    }

    @Test
	public void json_add_and_get_value() {
		final SenMLAPI senMLAPI = SenMLAPI.initJsonEncode();
		senMLAPI.addRecord(new Pair<Label, Double>(Label.VALUE, 30.0), new Pair<Label, String>(Label.BASE_NAME, "hello1"));
		senMLAPI.addRecord(new Pair<Label, Double>(Label.VALUE, 20.0),new Pair<Label, String>(Label.BASE_NAME, "hello2"));
		double v1 = (double) senMLAPI.getValue(Label.VALUE, 0);
		double v2 = (double) senMLAPI.getValue(Label.VALUE, 1);
		String bn1 = (String) senMLAPI.getValue(Label.BASE_NAME, 0);
		String bn2 = (String) senMLAPI.getValue(Label.BASE_NAME, 1);
		assertEquals(30.0, v1, EPSILON);
		assertEquals(20.0, v2, EPSILON);
		assertEquals("hello1", bn1);
		assertEquals("hello2", bn2);
	}

    @Test
    public void cbor_decode() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborDecode(
				hexStringToByteArray("81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF"));
		String bn = (String) senMLAPI.getValue(Label.BASE_NAME, 0);
		boolean vb = (boolean) senMLAPI.getValue(Label.BOOLEAN_VALUE, 0);
		assertEquals("mac:urn:dev:3290329032", bn);
		assertEquals(false, vb);
	}

    @Test
    public void cbor_decode_multiple_records() throws IOException {
		final SenMLAPI senMLAPI = SenMLAPI.initCborDecode(
				hexStringToByteArray("82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF"));
		String bn = (String) senMLAPI.getValue(Label.BASE_NAME, 1);

		assertEquals("mac:urn:dev:329032942", bn);
	}

	private byte[] hexStringToByteArray(String s) {
		byte[] data = new byte[s.length()/2];
		for (int i = 0; i < data.length; i ++) {
			data[i] = (byte) ((Character.digit(s.charAt(i*2), 16) << 4)
					+ Character.digit(s.charAt(i*2 + 1), 16));
		}
		return data;
	}

}