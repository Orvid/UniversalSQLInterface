/* Generated By:JavaCC: Do not edit this line. SQLConstants.java */
package com.mckoi.database.sql;

public interface SQLConstants {

  int EOF = 0;
  int STAR = 7;
  int ASSIGNMENT = 8;
  int EQUALS = 9;
  int GR = 10;
  int LE = 11;
  int GREQ = 12;
  int LEEQ = 13;
  int NOTEQ = 14;
  int DIVIDE = 15;
  int ADD = 16;
  int SUBTRACT = 17;
  int CONCAT = 18;
  int BOOLEAN_LITERAL = 19;
  int NULL_LITERAL = 20;
  int REGEX_LITERAL = 21;
  int DROP = 22;
  int SHOW = 23;
  int ALTER = 24;
  int SELECT = 25;
  int UPDATE = 26;
  int CREATE = 27;
  int DELETE = 28;
  int INSERT = 29;
  int COMMIT = 30;
  int COMPACT = 31;
  int EXPLAIN = 32;
  int ROLLBACK = 33;
  int OPTIMIZE = 34;
  int DESCRIBE = 35;
  int SHUTDOWN = 36;
  int IS = 37;
  int AS = 38;
  int ON = 39;
  int IF = 40;
  int TO = 41;
  int NO = 42;
  int ALL = 43;
  int ANY = 44;
  int SET = 45;
  int USE = 46;
  int ASC = 47;
  int OLD = 48;
  int NEW = 49;
  int SQLADD = 50;
  int FOR = 51;
  int ROW = 52;
  int EACH = 53;
  int CALL = 54;
  int BOTH = 55;
  int SOME = 56;
  int FROM = 57;
  int LEFT = 58;
  int DESC = 59;
  int INTO = 60;
  int JOIN = 61;
  int TRIM = 62;
  int VIEW = 63;
  int LOCK = 64;
  int WITH = 65;
  int USER = 66;
  int CAST = 67;
  int LONG = 68;
  int NAME = 69;
  int JAVA = 70;
  int AFTER = 71;
  int START = 72;
  int COUNT = 73;
  int WHERE = 74;
  int CYCLE = 75;
  int CACHE = 76;
  int RIGHT = 77;
  int TABLE = 78;
  int LIMIT = 79;
  int INNER = 80;
  int INDEX = 81;
  int CROSS = 82;
  int OUTER = 83;
  int CHECK = 84;
  int USING = 85;
  int UNION = 86;
  int GRANT = 87;
  int USAGE = 88;
  int SQLRETURN = 89;
  int BEFORE = 90;
  int UNLOCK = 91;
  int ACTION = 92;
  int GROUPS = 93;
  int REVOKE = 94;
  int OPTION = 95;
  int PUBLIC = 96;
  int EXCEPT = 97;
  int IGNORE = 98;
  int SCHEMA = 99;
  int EXISTS = 100;
  int VALUES = 101;
  int HAVING = 102;
  int UNIQUE = 103;
  int SQLCOLUMN = 104;
  int RETURNS = 105;
  int ACCOUNT = 106;
  int LEADING = 107;
  int NATURAL = 108;
  int BETWEEN = 109;
  int TRIGGER = 110;
  int SQLDEFAULT = 111;
  int VARYING = 112;
  int EXECUTE = 113;
  int CALLBACK = 114;
  int MINVALUE = 115;
  int MAXVALUE = 116;
  int FUNCTION = 117;
  int SEQUENCE = 118;
  int RESTRICT = 119;
  int PASSWORD = 120;
  int TRAILING = 121;
  int GROUPBY = 122;
  int ORDERBY = 123;
  int DEFERRED = 124;
  int DISTINCT = 125;
  int LANGUAGE = 126;
  int INCREMENT = 127;
  int PROCEDURE = 128;
  int CHARACTER = 129;
  int IMMEDIATE = 130;
  int INITIALLY = 131;
  int TEMPORARY = 132;
  int INTERSECT = 133;
  int PRIVILEGES = 134;
  int CONSTRAINT = 135;
  int DEFERRABLE = 136;
  int REFERENCES = 137;
  int PRIMARY = 138;
  int FOREIGN = 139;
  int KEY = 140;
  int INDEX_NONE = 141;
  int INDEX_BLIST = 142;
  int GROUPMAX = 143;
  int COLLATE = 144;
  int PRIMARY_STRENGTH = 145;
  int SECONDARY_STRENGTH = 146;
  int TERTIARY_STRENGTH = 147;
  int IDENTICAL_STRENGTH = 148;
  int NO_DECOMPOSITION = 149;
  int CANONICAL_DECOMPOSITION = 150;
  int FULL_DECOMPOSITION = 151;
  int BIT = 152;
  int INT = 153;
  int REAL = 154;
  int CLOB = 155;
  int BLOB = 156;
  int CHAR = 157;
  int TEXT = 158;
  int DATE = 159;
  int TIME = 160;
  int FLOAT = 161;
  int BIGINT = 162;
  int DOUBLE = 163;
  int STRING = 164;
  int BINARY = 165;
  int NUMERIC = 166;
  int DECIMAL = 167;
  int BOOLEAN = 168;
  int TINYINT = 169;
  int INTEGER = 170;
  int VARCHAR = 171;
  int SMALLINT = 172;
  int VARBINARY = 173;
  int TIMESTAMP = 174;
  int JAVA_OBJECT = 175;
  int LONGVARCHAR = 176;
  int LONGVARBINARY = 177;
  int TRANSACTIONISOLATIONLEVEL = 178;
  int AUTOCOMMIT = 179;
  int READCOMMITTED = 180;
  int READUNCOMMITTED = 181;
  int REPEATABLEREAD = 182;
  int SERIALIZABLE = 183;
  int CASCADE = 184;
  int CURRENT_TIME = 185;
  int CURRENT_DATE = 186;
  int CURRENT_TIMESTAMP = 187;
  int LIKE = 188;
  int REGEX = 189;
  int AND = 190;
  int OR = 191;
  int IN = 192;
  int NOT = 193;
  int NUMBER_LITERAL = 194;
  int STRING_LITERAL = 195;
  int QUOTED_VARIABLE = 196;
  int IDENTIFIER = 197;
  int DOT_DELIMINATED_REF = 198;
  int QUOTED_DELIMINATED_REF = 199;
  int JAVA_OBJECT_ARRAY_REF = 200;
  int CTALIAS = 201;
  int GLOBVARIABLE = 202;
  int QUOTEDGLOBVARIABLE = 203;
  int PARAMETER_REF = 204;
  int LETTER = 205;
  int DIGIT = 206;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<token of kind 5>",
    "<token of kind 6>",
    "\"*\"",
    "\"=\"",
    "\"==\"",
    "\">\"",
    "\"<\"",
    "\">=\"",
    "\"<=\"",
    "<NOTEQ>",
    "\"/\"",
    "\"+\"",
    "\"-\"",
    "\"||\"",
    "<BOOLEAN_LITERAL>",
    "\"null\"",
    "<REGEX_LITERAL>",
    "\"drop\"",
    "\"show\"",
    "\"alter\"",
    "\"select\"",
    "\"update\"",
    "\"create\"",
    "\"delete\"",
    "\"insert\"",
    "\"commit\"",
    "\"compact\"",
    "\"explain\"",
    "\"rollback\"",
    "\"optimize\"",
    "\"describe\"",
    "\"shutdown\"",
    "\"is\"",
    "\"as\"",
    "\"on\"",
    "\"if\"",
    "\"to\"",
    "\"no\"",
    "\"all\"",
    "\"any\"",
    "\"set\"",
    "\"use\"",
    "\"asc\"",
    "\"old\"",
    "\"new\"",
    "\"add\"",
    "\"for\"",
    "\"row\"",
    "\"each\"",
    "\"call\"",
    "\"both\"",
    "\"some\"",
    "\"from\"",
    "\"left\"",
    "\"desc\"",
    "\"into\"",
    "\"join\"",
    "\"trim\"",
    "\"view\"",
    "\"lock\"",
    "\"with\"",
    "\"user\"",
    "\"cast\"",
    "\"long\"",
    "\"name\"",
    "\"java\"",
    "\"after\"",
    "\"start\"",
    "\"count\"",
    "\"where\"",
    "\"cycle\"",
    "\"cache\"",
    "\"right\"",
    "\"table\"",
    "\"limit\"",
    "\"inner\"",
    "\"index\"",
    "\"cross\"",
    "\"outer\"",
    "\"check\"",
    "\"using\"",
    "\"union\"",
    "\"grant\"",
    "\"usage\"",
    "\"return\"",
    "\"before\"",
    "\"unlock\"",
    "\"action\"",
    "\"groups\"",
    "\"revoke\"",
    "\"option\"",
    "\"public\"",
    "\"except\"",
    "\"ignore\"",
    "\"schema\"",
    "\"exists\"",
    "\"values\"",
    "\"having\"",
    "\"unique\"",
    "\"column\"",
    "\"returns\"",
    "\"account\"",
    "\"leading\"",
    "\"natural\"",
    "\"between\"",
    "\"trigger\"",
    "\"default\"",
    "\"varying\"",
    "\"execute\"",
    "\"callback\"",
    "\"minvalue\"",
    "\"maxvalue\"",
    "\"function\"",
    "\"sequence\"",
    "\"restrict\"",
    "\"password\"",
    "\"trailing\"",
    "\"group by\"",
    "\"order by\"",
    "\"deferred\"",
    "\"distinct\"",
    "\"language\"",
    "\"increment\"",
    "\"procedure\"",
    "\"character\"",
    "\"immediate\"",
    "\"initially\"",
    "\"temporary\"",
    "\"intersect\"",
    "\"privileges\"",
    "\"constraint\"",
    "\"deferrable\"",
    "\"references\"",
    "\"primary\"",
    "\"foreign\"",
    "\"key\"",
    "\"index_none\"",
    "\"index_blist\"",
    "\"group max\"",
    "\"collate\"",
    "\"primary_strength\"",
    "\"secondary_strength\"",
    "\"tertiary_strength\"",
    "\"identical_strength\"",
    "\"no_decomposition\"",
    "\"canonical_decomposition\"",
    "\"full_decomposition\"",
    "\"bit\"",
    "\"int\"",
    "\"real\"",
    "\"clob\"",
    "\"blob\"",
    "\"char\"",
    "\"text\"",
    "\"date\"",
    "\"time\"",
    "\"float\"",
    "\"bigint\"",
    "\"double\"",
    "\"string\"",
    "\"binary\"",
    "\"numeric\"",
    "\"decimal\"",
    "\"boolean\"",
    "\"tinyint\"",
    "\"integer\"",
    "\"varchar\"",
    "\"smallint\"",
    "\"varbinary\"",
    "\"timestamp\"",
    "\"java_object\"",
    "\"longvarchar\"",
    "\"longvarbinary\"",
    "\"transaction isolation level\"",
    "\"auto commit\"",
    "\"read committed\"",
    "\"read uncommitted\"",
    "\"repeatable read\"",
    "\"serializable\"",
    "\"cascade\"",
    "\"current_time\"",
    "\"current_date\"",
    "\"current_timestamp\"",
    "\"like\"",
    "\"regex\"",
    "\"and\"",
    "\"or\"",
    "\"in\"",
    "\"not\"",
    "<NUMBER_LITERAL>",
    "<STRING_LITERAL>",
    "<QUOTED_VARIABLE>",
    "<IDENTIFIER>",
    "<DOT_DELIMINATED_REF>",
    "<QUOTED_DELIMINATED_REF>",
    "<JAVA_OBJECT_ARRAY_REF>",
    "<CTALIAS>",
    "<GLOBVARIABLE>",
    "<QUOTEDGLOBVARIABLE>",
    "\"?\"",
    "<LETTER>",
    "<DIGIT>",
    "\";\"",
    "\"(\"",
    "\")\"",
    "\",\"",
  };

}
