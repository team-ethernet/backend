package teamethernet.senml_api;

import org.junit.Before;
import org.junit.Test;

public class SenMLAPITest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void json_many_parameters() {
		SenMLAPI = new SenMlAPI.initJsonEncode();
		Pair bn = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair v = new Pair<>(Label.VALUE, 30.00);
		Pair vb = new Pair<>(Label.BOOLEAN_VALUE, false);
		Pair u = new Pair<>(Label.UNIT, "dB");
		SenMLAPI.addRecord(bn, v, vb, u);
		assertTrue(SenMLAPI.endSenML()).isEqualTo("[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.000000,\"vb\":false,\"u\":\"dB\"}]");
    }

    @Test
    public void json_multiple_records() {
		SenMLAPI = new SenMlAPI.initJsonEncode();
		Pair bn1 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:3290329032");
		Pair bn2 = new Pair<>(Label.BASE_NAME, "mac:urn:dev:329032942");
		Pair bver = new Pair<>(Label.BASE_VERSION, 0);
		Pair vs = new Pair<>(Label.STRING_VALUE, "hello");
		Pair ut = new Pair<>(Label.UPDATE_TIME, "30.00");

		SenMLAPI.addRecord(bn1, bver);
		SenMLAPI.addRecord(bn2, vs, ut);
		assertTrue(SenMLAPI.endSenML()).isEqualTo("[{\"bn\":\"mac:urn:dev:3290329032\",\"bver\":0},{\"bn\":\"mac:urn:dev:329032942\",\"vs\":\"hello\",\"ut\":\"30.00\"}]");
    }


}