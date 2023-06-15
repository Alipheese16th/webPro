import Vue from 'vue'
const ZonedDateTime = require('@js-joda/core').ZonedDateTime
const ZoneId = require('@js-joda/core').ZoneId
require('@js-joda/timezone')

Vue.mixin({
  methods: {
    // 타밍존 파라미터를 받아 해당 타임존의 Date 객체를 반환
    // ex) const date = this.getTzDateObj('Asia/Seoul') => Date 객체
    getTzDateObj(tz) {
      const d = ZonedDateTime.parse(ZonedDateTime.now(ZoneId.of(tz)).toString())
      const dateTime = d._dateTime
      const dt = dateTime._date
      const tm = dateTime._time
      return new Date(dt._year, dt._month - 1, dt._day, tm._hour, tm._minute, tm._second)
    },
    // 타임존 파라미터와 구분자를 받아 YYYY,MM,DD,HH:MM:SS 정보를 string으로 return
    // ex) const dateTime = this.getTzDateTime('Asia/Seoul', '-') => 2020-12-17 13:20:44
    getTzDateTime(tz, separator = '') {
      const date = this.getTzDateObj(tz)

      const yyyy = date.getFullYear()
      const mm = date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      const hh = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const min = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      const ss = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      return ''.concat(yyyy).concat(separator).concat(mm).concat(separator).concat(dd).concat(' ').concat(hh).concat(':').concat(min).concat(':').concat(ss)
    },
    // 타임존 파라미터와 구분자를 받아 YYYY,MM,DD 정보를 string으로 return
    // ex) const date = this.getTzDate('Asia/Seoul', '-') => 2020-12-17
    getTzDate(tz, separator = '', day = 0, month = 0) {
      const date = this.getTzDateObj(tz)
      date.setDate(date.getDate() - day)
      date.setMonth(date.getMonth() - month)

      const yyyy = date.getFullYear()
      const mm = date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      const dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      return ''.concat(yyyy).concat(separator).concat(mm).concat(separator).concat(dd)
    },
    // 타임존 파라미터와 구분자를 받아 HH,MM,SS 정보를 string으로 return
    // ex) const time = this.getTzTime('Asia/Seoul', '-') => 13:20:44
    getTzTime(tz, separator = '', h = 0, m = 0) {
      const date = this.getTzDateObj(tz)
      date.setHours(date.getHours() - h)
      date.setMinutes(date.getMinutes() - m)

      const hh = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      const min = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      const ss = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()

      return ''.concat(hh).concat(separator).concat(min).concat(separator).concat(ss)
    },
  },
})
