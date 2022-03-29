package kr.yhs.traffic.ui.theme

import androidx.compose.ui.graphics.Color
import kr.yhs.traffic.ui.WearableColors

enum class BusColor(
    val color: Color,
    private val typeCode: Int
) {
    Default(
        WearableColors.primary,
        0
    ),
    SeoulAirport(
        Color(0xff8b4513),
        1001
    ),
    SeoulTown(
        Color(0xff5bb025),
        1002
    ),
    SeoulTrunk(
        Color(0xff3d5bab),
        1003
    ),
    SeoulBranch(
        Color(0xff5bb025),
        1004
    ),
    SeoulCircular(
        Color(0xfff99d1c),
        1004
    ),
    SeoulWideArea(
        Color(0xffF72f08),
        1004
    ),
    GyeonggiCityWideArea(
        Color(0xffFF0000),
        2011
    ),
    GyeonggiCitySeat(
        Color(0xff0075C8),
        2012
    ),
    GyeonggiCityNormal(
        Color(0xff33CC99),
        2013
    ),
    GyeonggiCityWideAreaRapid(
        Color(0xff000000),
        2014
    ),
    GyeonggiCustomized(
        Color(0xffB62367),
        2015
    ),
    GyeonggiCircular(
        Color(0xffFF0000),
        2016
    ),
    GyeonggiCountryWideArea(
        Color(0xffFF0000),
        2021
    ),
    GyeonggiCountrySeat(
        Color(0xff0075C8),
        2022
    ),
    GyeonggiCountryNormal(
        Color(0xff33CC99),
        2023
    ),
    GyeonggiTown(
        Color(0xffF99D1C),
        2030
    ),
    GyeonggiOutSeat(
        Color(0xff0075C8),
        2042
    ),
    GyeonggiOutNormal(
        Color(0xffa800ff),
        2043
    ),
    GyeonggiAirport(
        Color(0xffaa9872),
        2051
    ),
    GyeonggiAirportSeat(
        Color(0xff0075C8),
        2052
    ),
    GyeonggiAirportNormal(
        Color(0xff8b4513),
        2053
    ),
    IncheonBranch(
        Color(0xff5bb025),
        3001
    ),
    IncheonTrunk(
        Color(0xff3366cc),
        3002
    ),
    IncheonSeat(
        Color(0xff3d5bab),
        3003
    ),
    IncheonWideArea(
        Color(0xfff72f08),
        3004
    ),
    IncheonTown(
        Color(0xff5bb025),
        3006
    ),
    IncheonWideAreaRapid(
        Color(0xff000000),
        3008
    ),
    IncheonBranchCircular(
        Color(0xff5bb025),
        3009
    )
}