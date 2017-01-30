import {
Router, Route, Link
IndexRoute
Redirect
} from 'react-router'

# Link #
to 字符串 或 对象

```
<Router>
	<Route path="/" component={App}>
		<Route path="about" component={About} />
		<Route path="inbox" component={Inbox}>
			<Route path="messages/:id" component={Message} />
		</Route>
	</Route>
</Router>
```

```
import { Redirect } from 'react-router'

render((
  <Router>
    <Route path="/" component={App}>
      <IndexRoute component={Dashboard} />
      <Route path="about" component={About} />

      <Route path="inbox" component={Inbox}>
        {/* Redirect /inbox/messages/:id to /messages/:id */}
        <Redirect from="messages/:id" to="/messages/:id" />
      </Route>

      <Route component={Inbox}>
        <Route path="messages/:id" component={Message} />
      </Route>
    </Route>
  </Router>
), document.body)
```

