package io.github.davianalbarran.renderer

object RendererConstants {
//    0	Reset or normal	All attributes become turned off
//    1	Bold or increased intensity	As with faint, the color change is a PC (SCO / CGA) invention.[26][better source needed]
    const val SGR_BOLD = "1"
//    2	Faint, decreased intensity, or dim	May be implemented as a light font weight like bold.[27]
    const val SGR_FAINT = "2"
//    3	Italic	Not widely supported. Sometimes treated as inverse or blink.[26]
    const val SGR_ITALIC = "3"
//    4	Underline	Style extensions exist for Kitty, VTE, mintty, iTerm2 and Konsole.[28][29][30]
    const val SGR_UNDERLINE = "4"
//    5	Slow blink	Sets blinking to less than 150 times per minute
    const val SGR_SLOW_BLINK = "5"
//    6	Rapid blink	MS-DOS ANSI.SYS, 150+ per minute; not widely supported
    const val SGR_RAPID_BLINK = "6"
//    7	Reverse video or invert	Swap foreground and background colors.
    const val SGR_INVERT_COLORS = "7"
//    8	Conceal or hide	Not widely supported.
    const val SGR_HIDE = "8"
//    9	Crossed-out, or strike	Characters legible but marked as if for deletion. Not supported in Terminal.app.
    const val SGR_STRIKETHROUGH = "9"
//    10	Primary (default) font
    const val SGR_PRIMARY_FONT = "10"
//    11–19	Alternative font	Select alternative font n − 10
    const val SGR_ALTERNATIVE_FONT = "11"
//    20	Fraktur (Gothic)	Rarely supported
    const val SGR_FRAKTUR = "20"
//    21	Doubly underlined; or: not bold	Double-underline per ECMA-48,[16]: 8.3.117  but instead disables bold intensity on several terminals, including in the Linux kernel's console before version 4.17.[31]
    const val SGR_DOUBLY_UNDERLINED = "21"
//    22	Normal intensity	Neither bold nor faint; color changes where intensity is implemented as such.
    const val SGR_NORMAL_FONT_WEIGHT = "22"
//    23	Neither italic, nor blackletter
    const val SGR_NOT_ITALIC_NOT_BLACKLETTER = "23"
//    24	Not underlined	Neither singly nor doubly underlined
    const val SGR_NOT_UNDERLINED = "24"
//    25	Not blinking	Turn blinking off
    const val SGR_NOT_BLINKING = "25"
//    26	Proportional spacing	ITU T.61 and T.416, not known to be used on terminals
    const val SGR_PROPORTIONAL_SPACING = "26"
//    27	Not reversed
    const val SGR_NOT_REVERSED = "27"
//    28	Reveal	Not concealed
    const val SGR_REVEAL = "28"
//    29	Not crossed out
    const val SGR_NOT_STRIKETHROUGH = "29"
//    30–37	Set foreground color
//    38	Set foreground color	Next arguments are 5;n or 2;r;g;b
    const val SGR_SET_FOREGROUND_256_OR_TRUE_COLOR = "38"
    const val SGR_SET_FOREGROUND_256 = "${SGR_SET_FOREGROUND_256_OR_TRUE_COLOR};5"
    const val SGR_SET_FOREGROUND_TRUE_COLOR = "${SGR_SET_FOREGROUND_256_OR_TRUE_COLOR};2"
//    39	Default foreground color	Implementation defined (according to standard)
    const val SGR_SET_DEFAULT_FOREGROUND_COLOR = "39"
//    40–47	Set background color
//    48	Set background color	Next arguments are 5;n or 2;r;g;b
    const val SGR_SET_BACKGROUND_256_OR_TRUE_COLOR = "48"
    const val SGR_SET_BACKGROUND_256 = "${SGR_SET_BACKGROUND_256_OR_TRUE_COLOR};5"
    const val SGR_SET_BACKGROUND_TRUE_COLOR = "${SGR_SET_BACKGROUND_256_OR_TRUE_COLOR};2"
//    49	Default background color	Implementation defined (according to standard)
    const val SGR_SET_DEFAULT_BACKGROUND_COLOR = "49"
//    50	Disable proportional spacing	T.61 and T.416
    const val SGR_DISABLE_PROPORTIONAL_SPACING = "50"
//    51	Framed	Implemented as "emoji variation selector" in mintty.[32]
    const val SGR_FRAMED = "51"
//    52	Encircled
    const val SGR_ENCIRCLED = "52"
//    53	Overlined	Not supported in Terminal.app
    const val SGR_OVERLINED = "53"
//    54	Neither framed nor encircled
    const val SGR_NOT_FRAMED_NOT_ENCIRCLED = "54"
//    55	Not overlined
    const val SGR_NOT_OVERLINED = "55"
//    58	Set underline color	Not in standard; implemented in Kitty, VTE, mintty, and iTerm2.[28][29] Next arguments are 5;n or 2;r;g;b.
    const val SGR_SET_UNDERLINE_COLOR = "58"
//    59	Default underline color	Not in standard; implemented in Kitty, VTE, mintty, and iTerm2.[28][29]
//    60	Ideogram underline or right side line	Rarely supported
//    61	Ideogram double underline, or double line on the right side
//    62	Ideogram overline or left side line
//    63	Ideogram double overline, or double line on the left side
//    64	Ideogram stress marking
//    65	No ideogram attributes	Reset the effects of all of 60–64
//    73	Superscript	Implemented only in mintty[32]
    const val SGR_SUPERSCRIPT = "73"
//    74	Subscript
    const val SGR_SUBSCRIPT = "74"
//    75	Neither superscript nor subscript
    const val SGR_NOT_SUPERSCRIPT_NOT_SUBSCRIPT = "75"
//    90–97	Set bright foreground color	Not in standard; originally implemented by aixterm[17]
//    100–107	Set bright background color

    const val SGR_PREFIX = "\u001b["
    const val SGR_SUFFIX = "m"
    const val RESET_CODE = "${SGR_PREFIX}0$SGR_SUFFIX"
}