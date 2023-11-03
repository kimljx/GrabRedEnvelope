package com.carlos.hongbaoqiang.util

import android.util.Log
import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.io.InputStream
import java.util.*

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */

/**
 * Github: https://github.com/xbdcc/.
 * Created by 作者 on 2016/2/20.
 */
object UpdateInfoParser {

    private val TAG = "Version"

    fun getUpdateInfo(inputStream: InputStream): List<UpdateInfo> {

        var infos = mutableListOf<UpdateInfo>()
        var info = UpdateInfo()

        val parser = Xml.newPullParser()
        try {
            parser.setInput(inputStream, "utf-8")
            var type = parser.eventType
            Log.i(TAG, "服务器版本信息")
            while (type != XmlPullParser.END_DOCUMENT) {
                when (type) {
                    XmlPullParser.START_DOCUMENT -> infos = ArrayList()
                    XmlPullParser.START_TAG -> if (parser.name == "information") {
                        info = UpdateInfo()
                    } else if ("versionCode" == parser.name) {
                        info.versionCode = Integer.valueOf(parser.nextText())
                        Log.i(TAG, info.versionCode.toString() + "")
                    } else if ("versionName" == parser.name) {
                        info.versionName = parser.nextText()
                        Log.i(TAG, info.versionName)
                    } else if ("apkUrl" == parser.name) {
                        info.apkUrl = parser.nextText()
                        Log.i(TAG, info.apkUrl)
                    } else if ("description" == parser.name) {
                        info.description = parser.nextText()
                        Log.i(TAG, info.description)
                    }
                    XmlPullParser.END_TAG -> if (parser.name == "information") {
                        infos.add(info)
                    }
                    else -> {
                    }
                }
                type = parser.next()
            }

        } catch (e: IOException) {
            e.printStackTrace()
            return arrayListOf(UpdateInfo())
        }

        return infos
    }

}