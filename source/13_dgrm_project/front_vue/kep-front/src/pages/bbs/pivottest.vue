<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--자료실(KO)-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('LB00000125') }}</span>
    </div>
    <div class="work_title">
      <!--자료실(KO)-->
      <h2>{{ $t('LB00000125') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--분류(KO)-->
                <div class="label_tit">{{ $t('LB00000123') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.doc_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000013"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="reset()">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--자료실 목록(KO)-->
        <h3>{{ $t('LB00000126') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <div>
            <!--등록-->
            <v-btn v-if="searchdatas.userApi" color="primary" depressed height="30" @click="openForm()">{{ $t('LB00000111') }}</v-btn>
          </div>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div id="realpivotDiv" style="width: 100%; height: 500px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider } from 'realgrid'

let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '자료실 목록',
    key(route) {
      return `/bbs/arch/${route.params.catalog}`
    },
  },
  data() {
    return {
      codeid: ['CO00000013'],
      codes: {
        CO00000013: [{ cd: '', cd_nm: '' }],
      },
      totalcnt: 0,
      searchdatas: {
        bbs_type_cd: 'ARCH',
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        doc_type_cd: '',
        userApi: false,
        bbs_doc_no: '',
      },
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async created() {
    // eslint-disable-next-line no-unused-vars
    const rows = await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        res.CO00000013.forEach((i) => {
          if (i.up_cd === 'ARCH') {
            this.codes.CO00000013.push(i)
          }
        })
        this.codes.CO00000013.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    const usrinfo = this.$store.getters['auth/getUser']
    usrinfo.roleTypeCds.forEach((i) => {
      if (i === 'M01' || i === 'C01') {
        // 사용자 유저 권한 체크 role type cd = M01,C01  아닐경우 등록 버튼 숨기기 , 상세페이지로만이동
        this.searchdatas.userApi = true
      }
    })
  },
  mounted() {
    dataProvider = new LocalDataProvider(false)
    const gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)

    const pivot = new RealPivot('realpivotDiv')

    const fields = [
      {
        fieldName: 'OrderID',
      },
      {
        fieldName: 'CustomerID',
      },
      {
        fieldName: 'EmployeeID',
      },
      {
        fieldName: 'OrderDate',
        dataType: 'datetime',
        datetimeFormat: 'yyyy-MM-dd a hh:mm:ss',
        amText: '오전',
        pmText: '오후',
      },
      {
        fieldName: 'CompanyName',
      },
      {
        fieldName: 'Country',
      },
      {
        fieldName: 'Phone',
      },
      {
        fieldName: 'ProductName',
      },
      {
        fieldName: 'QuantityPerUnit',
      },
      {
        fieldName: 'Quantity',
        dataType: 'number',
      },
      {
        fieldName: 'UnitPrice',
        dataType: 'number',
      },
    ]

    const columns = [
      {
        name: 'OrderID',
        fieldName: 'OrderID',
        header: { text: 'OrderID' },
      },
      {
        name: 'CustomerID',
        fieldName: 'CustomerID',
        header: { text: 'CustomerID' },
      },
      {
        name: 'EmployeeID',
        fieldName: 'EmployeeID',
        header: { text: 'EmployeeID' },
      },
      {
        name: 'OrderDate',
        fieldName: 'OrderDate',
        header: { text: 'OrderDate' },
      },
      {
        name: 'CompanyName',
        fieldName: 'CompanyName',
        header: { text: 'CompanyName' },
      },
      {
        name: 'Country',
        fieldName: 'Country',
        header: { text: 'Country' },
      },
      {
        name: 'Phone',
        fieldName: 'Phone',
        header: { text: 'Phone' },
      },
      {
        name: 'ProductName',
        fieldName: 'ProductName',
        header: { text: 'ProductName' },
      },
      {
        name: 'QuantityPerUnit',
        fieldName: 'QuantityPerUnit',
        header: { text: 'QuantityPerUnit' },
      },
      {
        name: 'Quantity',
        fieldName: 'Quantity',
        header: { text: 'Quantity' },
      },
      {
        name: 'UnitPrice',
        fieldName: 'UnitPrice',
        header: { text: 'UnitPrice' },
      },
    ]

    gridView.setColumns(columns)

    const rows = [
      {
        OrderID: '10248',
        CustomerID: 'VINET',
        CustomerID2: 'VINAC',
        EmployeeID: '5',
        OrderDate: '1996-07-04 오전 12:00:00',
        RequiredDate: '1996-08-01 오전 12:00:00',
        ShippedDate: '1996-07-16 오전 12:00:00',
        ShipVia: '3',
        Freight: '32.3800',
        ShipName: 'Vins et alcools Chevalier\r\nVins et alcools Chevalier\r\nVins et alcools Chevalier',
        ShipAddress: '59 rue de l\u0027Abbaye',
        ShipCity: 'Reims',
        ShipRegion: null,
        ShipPostalCode: '51100',
        ShipCountry: 'France',
        CompanyName: 'Vins et alcools Chevalier\r\n',
        Country: 'France',
        Shipping: 'True',
        SmallFlag: 'http://dev.demo.realgrid.net/img/demo/smallflag/fr.png',
        NormalFlag: 'http://dev.demo.realgrid.net/img/demo/normalflag/fr.png',
        Phone: '26.47.15.10',
        ProductName: 'Queso Cabrales',
        QuantityPerUnit: '1 kg pkg.',
        Quantity: '1200',
        UnitPrice: '14.0000',
      },
      {
        OrderID: '10249',
        CustomerID: 'VINET',
        CustomerID2: 'VINAC',
        EmployeeID: '5',
        OrderDate: '1997-07-04 오전 12:00:00',
        RequiredDate: '1997-08-01 오전 12:00:00',
        ShippedDate: '1997-07-16 오전 12:00:00',
        ShipVia: '3',
        Freight: '32.3800',
        ShipName: 'Vins et alcools Chevalier\r\nVins et alcools Chevalier\r\nVins et alcools Chevalier',
        ShipAddress: '59 rue de l\u0027Abbaye',
        ShipCity: 'Reims',
        ShipRegion: null,
        ShipPostalCode: '51100',
        ShipCountry: 'France',
        CompanyName: 'Vins et alcools Chevalier\r\n',
        Country: 'France',
        Shipping: 'True',
        SmallFlag: 'http://dev.demo.realgrid.net/img/demo/smallflag/fr.png',
        NormalFlag: 'http://dev.demo.realgrid.net/img/demo/normalflag/fr.png',
        Phone: '26.47.15.10',
        ProductName: 'Queso Cabrales',
        QuantityPerUnit: '1 kg pkg.',
        Quantity: '1200',
        UnitPrice: '14.0000',
      },
      {
        OrderID: '10250',
        CustomerID: 'VINET2',
        CustomerID2: 'VINAC2',
        EmployeeID: '5',
        OrderDate: '1997-07-04 오전 12:00:00',
        RequiredDate: '1997-08-01 오전 12:00:00',
        ShippedDate: '1997-07-16 오전 12:00:00',
        ShipVia: '3',
        Freight: '32.3800',
        ShipName: 'Vins et alcools Chevalier\r\nVins et alcools Chevalier\r\nVins et alcools Chevalier',
        ShipAddress: '59 rue de l\u0027Abbaye',
        ShipCity: 'Reims',
        ShipRegion: null,
        ShipPostalCode: '51100',
        ShipCountry: 'France',
        CompanyName: 'Vins et alcools Chevalier\r\n',
        Country: 'France',
        Shipping: 'True',
        SmallFlag: 'http://dev.demo.realgrid.net/img/demo/smallflag/fr.png',
        NormalFlag: 'http://dev.demo.realgrid.net/img/demo/normalflag/fr.png',
        Phone: '26.47.15.10',
        ProductName: 'Queso Cabrales',
        QuantityPerUnit: '1 kg pkg.',
        Quantity: '1200',
        UnitPrice: '14.0000',
      },
      {
        OrderID: '10251',
        CustomerID: 'VINET3',
        CustomerID2: 'VINAC3',
        EmployeeID: '5',
        OrderDate: '1998-07-04 오전 12:00:00',
        RequiredDate: '1998-08-01 오전 12:00:00',
        ShippedDate: '1998-07-16 오전 12:00:00',
        ShipVia: '3',
        Freight: '32.3800',
        ShipName: 'Vins et alcools Chevalier\r\nVins et alcools Chevalier\r\nVins et alcools Chevalier',
        ShipAddress: '59 rue de l\u0027Abbaye',
        ShipCity: 'Reims',
        ShipRegion: null,
        ShipPostalCode: '51100',
        ShipCountry: 'France',
        CompanyName: 'Vins et alcools Chevalier\r\n',
        Country: 'France',
        Shipping: 'True',
        SmallFlag: 'http://dev.demo.realgrid.net/img/demo/smallflag/fr.png',
        NormalFlag: 'http://dev.demo.realgrid.net/img/demo/normalflag/fr.png',
        Phone: '26.47.15.10',
        ProductName: 'Queso Cabrales',
        QuantityPerUnit: '1 kg pkg.',
        Quantity: '1200',
        UnitPrice: '14.0000',
      },
    ]

    dataProvider.setFields(fields)

    pivot.setDataProvider(dataProvider)

    dataProvider.setRows(rows)

    pivot.setFieldMapping([
      {
        name: 'OrderID',
        sourceField: 'OrderID',
        fieldHeader: '주문ID',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${label} 요약',
        prefix: '앞',
        suffix: '뒤',
        valueEnable: false,
      },
      {
        name: 'CustomerID',
        sourceField: 'CustomerID',
        fieldHeader: '고객사',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${value} 요약',
        valueEnable: false,
      },
      {
        name: 'EmployeeID',
        fieldHeader: '처리자',
        sourceField: 'EmployeeID',
        valueEnable: false,
      },
      {
        name: 'OrderYear',
        sourceField: 'OrderDate',
        dateType: 'year',
        fieldHeader: '영업년도',
        // eslint-disable-next-line no-template-curly-in-string
        displayFormat: '${value}년도',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${value}년도 요약',
        valueEnable: false,
      },
      {
        name: 'OrderHalf',
        sourceField: 'OrderDate',
        dateType: 'half',
        fieldHeader: '영업반기',
        displayLabels: {
          1: '상반기',
          2: '하반기',
        },
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${label} 요약',
        valueEnable: false,
      },
      {
        name: 'OrderQuarter',
        sourceField: 'OrderDate',
        dateType: 'quarter',
        fieldHeader: '영업분기',
        displayLabels: {
          1: '일사분기',
          2: '이사분기',
          3: '삼사분기',
          4: '사사분기',
        },
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${label} 요약',
        valueEnable: false,
      },
      {
        name: 'OrderMonth',
        sourceField: 'OrderDate',
        dateType: 'month',
        fieldHeader: '영업월',
        // eslint-disable-next-line no-template-curly-in-string
        displayFormat: '${value}월',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${label} 요약',
        valueEnable: false,
      },
      {
        name: 'WeekDay',
        sourceField: 'OrderDate',
        dateType: 'weekday',
        fieldHeader: '영업요일',
        displayLabels: {
          1: '일',
          2: '월',
          3: '화',
          4: '수',
          5: '목',
          6: '금',
          7: '토',
        },
        mustValues: [1, 2, 3, 4, 5, 6, 7],
        valueEnable: false,
      },
      {
        name: 'OrderDay',
        sourceField: 'OrderDate',
        dateType: 'day',
        fieldHeader: '영업일자',
        // eslint-disable-next-line no-template-curly-in-string
        displayFormat: '${value}일',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${value}일 요약',
        valueEnable: false,
      },
      {
        name: 'CompanyName',
        sourceField: 'CompanyName',
        fieldHeader: '생산자',
        valueEnable: false,
      },
      {
        name: 'ProductName',
        sourceField: 'ProductName',
        fieldHeader: '품명 Product Name',
        // eslint-disable-next-line no-template-curly-in-string
        summaryFormat: '${value} 요약',
        valueEnable: false,
      },
      {
        name: 'Quantity',
        sourceField: 'Quantity',
        fieldHeader: '판매량',
        numberFormat: '#,##0.000',
        labelEnable: false,
      },
      {
        name: 'UnitPrice',
        sourceField: 'UnitPrice',
        fieldHeader: '판매액',
        numberFormat: '#0.00',
        labelEnable: false,
      },
    ])

    pivot.setPivotFields({
      columns: [
        {
          name: 'OrderYear',
          direction: 'descending',
        },
        {
          name: 'OrderQuarter',
          direction: 'ascending',
        },
      ],
      rows: ['CustomerID', 'ProductName'],
      values: [
        {
          name: 'Quantity',
          expression: 'sum',
        },
        {
          name: 'UnitPrice',
          expression: 'sum',
        },
      ],
    })

    pivot.drawView()
  },
  methods: {
    downloadAll(files) {
      files.forEach((i) => {
        this.download(i.key, i.name)
      })
    },
    download(key, fileName) {
      this.$axios
        .$get(`/api/v1/common/file-down/${key}`, {
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          // IE 10+
          if (window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(new Blob([res]), fileName)
          } else {
            // not IE
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(new Blob([res]))
            link.target = '_self'
            link.download = fileName
            link.click()
          }
        })
    },
    openForm() {
      this.$router.push({
        name: 'bbs-archedt-archedt',
        params: {
          schData: {
            bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
            bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
            doc_type_cd: this.searchdatas.doc_type_cd,
          },
        },
      })
    },
    async select() {
      // eslint-disable-next-line no-console
      await console.log(this.searchdatas)
      const rows = await this.$axios.$get(`/api/v1/common/bbs/arch`, { params: this.searchdatas })

      dataProvider.setRows(rows)
    },
    reset() {
      this.searchdatas.bbs_doc_ttl = ''
      this.searchdatas.bbs_doc_cntn = ''
      this.searchdatas.doc_type_cd = ''
      this.searchdatas.bbs_doc_no = ''
      // this.select()
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
  },
}
</script>
<style lang="scss" scoped></style>
