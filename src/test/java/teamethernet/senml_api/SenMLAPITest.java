package teamethernet.senml_api;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(Enclosed.class)
public class SenMLAPITest {

    private static final double EPSILON = Math.ulp(1.0);

    @RunWith(Enclosed.class)
    public static class JsonTests {

        public static class SmokeTests {

            @Test
            public void getRecord() throws IOException {
                final String inputjson = "[{\"bn\":\"mac:urn:dev:3290\",\"v\":30.0,\"vb\":false},{\"bn\":\"hello\",\"ut\":0.01,\"bt\":0.0,\"bu\":\"Watt\"}]";
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonDecode(inputjson);

                final String record1 = "{\"bn\":\"mac:urn:dev:3290\",\"v\":30.0,\"vb\":false}";
                final String record2 = "{\"bn\":\"hello\",\"ut\":0.01,\"bt\":0.0,\"bu\":\"Watt\"}";

                assertEquals(record1, senMLAPI.getRecord(0));
                assertEquals(record2, senMLAPI.getRecord(1));
            }

            @Test
            public void addAndGetValue() {
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonEncode();

                final Label.Pair bn1Pair = Label.BASE_NAME.attachValue("hello1");
                final Label.Pair v1Pair = Label.VALUE.attachValue(30.0);

                senMLAPI.addRecord(bn1Pair, v1Pair);

                final Label.Pair bn2Pair = Label.BASE_NAME.attachValue("hello2");
                final Label.Pair v2Pair = Label.VALUE.attachValue(20.0);

                senMLAPI.addRecord(bn2Pair, v2Pair);

                final String bn1 = senMLAPI.getValue(Label.BASE_NAME, 0);
                final double v1 = senMLAPI.getValue(Label.VALUE, 0);

                assertEquals("hello1", bn1);
                assertEquals(30.0, v1, EPSILON);

                final String bn2 = senMLAPI.getValue(Label.BASE_NAME, 1);
                final double v2 = senMLAPI.getValue(Label.VALUE, 1);

                assertEquals("hello2", bn2);
                assertEquals(20.0, v2, EPSILON);
            }

        }

        public static class EncodeTests {

            private SenMLAPI<JsonFormatter> senMLAPI;

            @Before
            public void setUp() {
                senMLAPI = SenMLAPI.initJsonEncode();
            }

            @Test
            public void empty() throws IOException {
                assertEquals("[]", senMLAPI.getSenML());
            }

            @Test
            public void manyParameters() throws IOException {
                final Label.Pair bn = Label.BASE_NAME.attachValue("mac:urn:dev:3290329032");
                final Label.Pair v = Label.VALUE.attachValue(30.0);
                final Label.Pair vb = Label.BOOLEAN_VALUE.attachValue(false);
                final Label.Pair u = Label.UNIT.attachValue("dB");

                senMLAPI.addRecord(bn, v, vb, u);

                final String expected = "[{\"bn\":\"mac:urn:dev:3290329032\",\"v\":30.0,\"vb\":false,\"u\":\"dB\"}]";
                assertEquals(expected, senMLAPI.getSenML());
            }

            @Test
            public void multipleRecords() throws IOException {
                final Label.Pair bn1 = Label.BASE_NAME.attachValue("mac:urn:dev:3290329032");
                final Label.Pair bver = Label.BASE_VERSION.attachValue(0);

                senMLAPI.addRecord(bn1, bver);

                final Label.Pair bn2 = Label.BASE_NAME.attachValue("mac:urn:dev:329032942");
                final Label.Pair vs = Label.STRING_VALUE.attachValue("hello");
                final Label.Pair ut = Label.UPDATE_TIME.attachValue(30.0);

                senMLAPI.addRecord(bn2, vs, ut);

                final String expected = "[{\"bn\":\"mac:urn:dev:3290329032\",\"bver\":0},{\"bn\":\"mac:urn:dev:329032942\",\"vs\":\"hello\",\"ut\":30.0}]";
                assertEquals(expected, senMLAPI.getSenML());
            }

        }

        public static class DecodeTests {

            @Test
            public void empty() throws IOException {
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonDecode("[]");

                assertEquals("[]", senMLAPI.getSenML());
            }

            @Test
            public void manyParameters() throws IOException {
                final String inputjson = "[{\"bn\":\"mac:urn:dev:3290\",\"v\":30.0,\"vb\":false}]";
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonDecode(inputjson);

                final String bn = senMLAPI.getValue(Label.BASE_NAME, 0);
                final double v = senMLAPI.getValue(Label.VALUE, 0);
                final boolean vb = senMLAPI.getValue(Label.BOOLEAN_VALUE, 0);

                assertEquals("mac:urn:dev:3290", bn);
                assertEquals(30.0, v, EPSILON);
                assertFalse(vb);
            }

            @Test
            public void multipleRecords() throws IOException {
                final String inputjson = "[{\"bn\":\"mac:urn:dev:3290\",\"v\":30.0,\"vb\":false},{\"bn\":\"hello\",\"ut\":0.01,\"bt\":0.0,\"bu\":\"Watt\"},{\"s\":3040.201}]";
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonDecode(inputjson);

                final String bn1 = senMLAPI.getValue(Label.BASE_NAME, 0);

                assertEquals("mac:urn:dev:3290", bn1);

                final String bn2 = senMLAPI.getValue(Label.BASE_NAME, 1);
                final double ut = senMLAPI.getValue(Label.UPDATE_TIME, 1);
                final double bt = senMLAPI.getValue(Label.BASE_TIME, 1);
                final String bu = senMLAPI.getValue(Label.BASE_UNIT, 1);

                assertEquals("hello", bn2);
                assertEquals(0.01, ut, EPSILON);
                assertEquals(0.0, bt, EPSILON);
                assertEquals("Watt", bu);

                final double s = senMLAPI.getValue(Label.SUM, 2);

                assertEquals(3040.201, s, EPSILON);
            }

            @Test
            public void allLabels() throws IOException {
                final String inputjson = "[{\"bn\":\"mac:urn:dev:3290\",\"v\":30.0,\"vb\":false},{\"bn\":\"hello\",\"ut\":0.01,\"bt\":0.0,\"bu\":\"Watt\"},{\"s\":3040.201}]";
                final SenMLAPI<JsonFormatter> senMLAPI = SenMLAPI.initJsonDecode(inputjson);

                final List<Label> labels0Expected = Arrays.asList(Label.BASE_NAME, Label.VALUE, Label.BOOLEAN_VALUE);
                final List labels0 = senMLAPI.getLabels(0);

                assertEquals(labels0Expected, labels0);

                final List<Label> labels1Expected = Arrays.asList(Label.BASE_NAME, Label.UPDATE_TIME, Label.BASE_TIME, Label.BASE_UNIT);
                final List labels1 = senMLAPI.getLabels(1);

                assertEquals(labels1Expected, labels1);

                final List<Label> labels2Expected = Collections.singletonList(Label.SUM);
                final List labels2 = senMLAPI.getLabels(2);

                assertEquals(labels2Expected, labels2);
            }

        }

    }

    @RunWith(Enclosed.class)
    public static class CborTests {

        public static class SmokeTests {

            @Test
            public void getRecord() throws IOException {
                final String cborData = "82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF";
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(hexStringToByteArray(cborData));

                final String record1 = "BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FF";
                final String record2 = "BF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF";

                assertEquals(record1, senMLAPI.getRecord(0));
                assertEquals(record2, senMLAPI.getRecord(1));
            }

            @Test
            public void addAndGetValue() {
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborEncode();

                final Label.Pair bn1Pair = Label.BASE_NAME.attachValue("hello1");
                final Label.Pair v1Pair = Label.VALUE.attachValue(30.0);

                senMLAPI.addRecord(bn1Pair, v1Pair);

                final Label.Pair bn2Pair = Label.BASE_NAME.attachValue("hello2");
                final Label.Pair v2Pair = Label.VALUE.attachValue(20.0);

                senMLAPI.addRecord(bn2Pair, v2Pair);

                final String bn1 = senMLAPI.getValue(Label.BASE_NAME, 0);
                final double v1 = senMLAPI.getValue(Label.VALUE, 0);

                assertEquals("hello1", bn1);
                assertEquals(30.0, v1, EPSILON);

                final String bn2 = senMLAPI.getValue(Label.BASE_NAME, 1);
                final double v2 = senMLAPI.getValue(Label.VALUE, 1);

                assertEquals("hello2", bn2);
                assertEquals(20.0, v2, EPSILON);
            }

        }

        public static class EncodeTests {

            private SenMLAPI<CborFormatter> senMLAPI;

            @Before
            public void setUp() {
                senMLAPI = SenMLAPI.initCborEncode();
            }

            @Test
            public void empty() throws IOException {
                assertEquals("80", senMLAPI.getSenML());
            }

            @Test
            public void manyParameters() throws IOException {
                final Label.Pair bn = Label.BASE_NAME.attachValue("mac:urn:dev:3290329032");
                final Label.Pair v = Label.VALUE.attachValue(30.0);
                final Label.Pair vb = Label.BOOLEAN_VALUE.attachValue(false);
                final Label.Pair u = Label.UNIT.attachValue("dB");

                senMLAPI.addRecord(bn, v, vb, u);

                final String expected = "81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF";
                assertEquals(expected, senMLAPI.getSenML());
            }

            @Test
            public void multipleRecords() throws IOException {
                final Label.Pair bn1 = Label.BASE_NAME.attachValue("mac:urn:dev:3290329032");
                final Label.Pair bver = Label.BASE_VERSION.attachValue(0);

                senMLAPI.addRecord(bn1, bver);

                final Label.Pair bn2 = Label.BASE_NAME.attachValue("mac:urn:dev:329032942");
                final Label.Pair vs = Label.STRING_VALUE.attachValue("hello");
                final Label.Pair ut = Label.UPDATE_TIME.attachValue(30.0);

                senMLAPI.addRecord(bn2, vs, ut);

                final String expected = "82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF";
                assertEquals(expected, senMLAPI.getSenML());
            }

        }

        public static class DecodeTests {

            @Test
            public void empty() throws IOException {
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(hexStringToByteArray("80"));

                assertEquals("80", senMLAPI.getSenML());
            }

            @Test
            public void manyParameters() throws IOException {
                final String cborData = "81BF62626E766D61633A75726E3A6465763A333239303332393033326176FB403E000000000000627662F46175626442FF";
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(hexStringToByteArray(cborData));

                String bn = senMLAPI.getValue(Label.BASE_NAME, 0);
                boolean vb = senMLAPI.getValue(Label.BOOLEAN_VALUE, 0);

                assertEquals("mac:urn:dev:3290329032", bn);
                assertFalse(vb);
            }

            @Test
            public void multipleRecords() throws Exception {
                final String cborData = "82BF62626E766D61633A75726E3A6465763A33323930333239303332646276657200FFBF62626E756D61633A75726E3A6465763A3332393033323934326276736568656C6C6F627574FB403E000000000000FF";
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(hexStringToByteArray(cborData));

                final String bn1 = senMLAPI.getValue(Label.BASE_NAME, 0);
                final int bver1 = senMLAPI.getValue(Label.BASE_VERSION, 0);

                assertEquals("mac:urn:dev:3290329032", bn1);
                assertEquals(0, bver1);

                final String bn2 = senMLAPI.getValue(Label.BASE_NAME, 1);
                final String vs2 = senMLAPI.getValue(Label.STRING_VALUE, 1);
                final double ut2 = senMLAPI.getValue(Label.UPDATE_TIME, 1);

                assertEquals("mac:urn:dev:329032942", bn2);
                assertEquals("hello", vs2);
                assertEquals(30.0, ut2, EPSILON);
            }

            @Test
            public void allLabels() throws IOException {
                final String cborData = "83A362626E706D61633A75726E3A6465763A333239306176F94F80627662F4A462626E6568656C6C6F627574FB3F847AE147AE147B626274F900006262756457617474A16173FB40A7C066E978D4FE";
                final SenMLAPI<CborFormatter> senMLAPI = SenMLAPI.initCborDecode(hexStringToByteArray(cborData));

                final List<Label> labels0Expected = Arrays.asList(Label.BASE_NAME, Label.VALUE, Label.BOOLEAN_VALUE);
                final List labels0 = senMLAPI.getLabels(0);

                assertEquals(labels0Expected, labels0);

                final List<Label> labels1Expected = Arrays.asList(Label.BASE_NAME, Label.UPDATE_TIME, Label.BASE_TIME, Label.BASE_UNIT);
                final List labels1 = senMLAPI.getLabels(1);

                assertEquals(labels1Expected, labels1);

                final List<Label> labels2Expected = Collections.singletonList(Label.SUM);
                final List labels2 = senMLAPI.getLabels(2);

                assertEquals(labels2Expected, labels2);
            }

        }

        private static byte[] hexStringToByteArray(String s) {
            byte[] data = new byte[s.length() / 2];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) ((Character.digit(s.charAt(i * 2), 16) << 4)
                        + Character.digit(s.charAt(i * 2 + 1), 16));
            }
            return data;
        }

    }

}