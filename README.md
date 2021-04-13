# Cliendroid
클리앙 사이트를 모바일로 볼 수 있는 어플리케이션 입니다.
현재 개발 중입니다.

* Kotlin
* Dagger 2
* Clean architecture
* [Mavericks](https://airbnb.io/mavericks/#/)
* Navigation
* Coroutine
* Retrofit, Coil, Jsoup
---
### Network Module
Html 페이지를 읽어서 이를 분석해서 데이터화 해서 화면에 보여지는 식으로 구성을 하였습니다.
실제로 서비스하는 앱이라면  javascript에다가 파서를 따로 만들어서 이를 다운받아서 처리 하는 형태로 구현을 하였겠지만, api 이용 및 로그인 처리 및 다양한 앱의 구성을 보여주기에는 이런 형태의 웹사이트 파싱이 좋겠다 생각하여서 retrofit의 converter를 별도로 구현하였습니다.

### Clean Architecture
클린 아키텍쳐를 모듈별로 나눠서 적용해 보았습니다.
API또한 위에서 설명한 것처럼 별도의 API 서버가 있는것처럼 network module에서 처리해서 제공해 줄 수 있도록 구성 하였습니다.

화면을 구성하는 presentation layer에서는 어떤 아키텍쳐를 사용할지 고민을 하였는데, mvvm을 사용하면서 단점이라고 느낀, view state가 엉키는 상황을 피하고자   단방향 데이터 구조를 사용하는 아키텍쳐인 mavericks를 사용하였습니다.

### Dagger 2
hilt를 사용할까 했지만, 최근에서야 아직도 베타인 상황이기에 Dagger를 사용하였습니다.

### Material Design
현재는 거의 없는 형태이긴하지만… 기본적인 material design의 형태를 따라가면서 style과 theme을 적용 하고 개발해 나가고 있습니다.
