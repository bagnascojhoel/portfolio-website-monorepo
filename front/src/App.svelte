<script lang="ts">
    import HomeScreen from '@components/screens/Home/Home.svelte';
    import ErrorToast from '@components/ErrorToast.svelte';
</script>

<svelte:head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
        href="https://fonts.googleapis.com/css2?family=Fira+Code:wght@300;500&display=swap"
        rel="stylesheet"
    />
    <link rel="icon" type="image/x-icon" href="assets/images/favicon.png" />
    <title>@bagnascojhoel | Software Development</title>
    <meta name="description" content="Software Development Portfolio" />
    <meta name="author" content="Jhoel Bagnasco" />
</svelte:head>

<ErrorToast />
<HomeScreen />

<style global lang="postcss">
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    body {
        position: relative;
    }

    ::selection {
        background-color: #fb923c;
        color: #f8fafc;
    }
</style>
