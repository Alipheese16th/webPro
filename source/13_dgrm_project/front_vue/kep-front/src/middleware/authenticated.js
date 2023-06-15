export default function ({ store, redirect }) {
  console.log('isLogin : ', store.getters['auth/isLogin']) // eslint-disable-line no-console
  // If the user is not authenticated
  if (!store.getters['auth/isLogin']) {
    return redirect('/authInfo')
  }
}
